package com.mobile2.projeto2.activities.criar_template_video;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.mobile2.projeto2.R;
import com.mobile2.projeto2.activities.main.MainActivity;
import com.mobile2.projeto2.entity.Word;
import com.mobile2.projeto2.repository.GeneralRepository;
import com.mobile2.projeto2.util.FilePath;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class CriarTemplateActivityVideo extends AppCompatActivity implements CriarTemplateVideoView {

    static final int REQUEST_VIDEO_CAPTURE = 1;

    @BindView(R.id.formulario_video)
    VideoView campoVideo;
    @BindView(R.id.progressBar)
    ProgressBar videoCompressProgressBar;
    @BindView(R.id.placeholder)
    ImageView mPlaceholder;
    private FFmpeg ffmpeg;


    private CriarTemplateVideoPresenter presenter;

    private String palavra;
    public EditText editText_PalavraVideo;


    private boolean videoAnexado = false;
    public String caminhoVideo =  Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + UUID.randomUUID() + ".mp4";
    public String caminhoVideoComprimido =  Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + UUID.randomUUID() + "-compressed.mp4";
    private int PICK_IMAGE_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_template_video);

        ButterKnife.bind(this);
        presenter = new CriarTemplateVideoPresenter(this);

        editText_PalavraVideo = findViewById(R.id.editText_PalavraVideo);
        loadFFMpegBinary();
        campoVideo.setOnPreparedListener( mp -> mp.setLooping(true));
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String command[] = {"-y", "-i", caminhoVideo ,  "-s", "640x480", "-r", "25", "-vcodec", "mpeg4", "-b:v", "128k", caminhoVideoComprimido};
            execFFmpegBinary(command);
        }
    }

    @OnClick(R.id.formulario_botao_video)
    public void addVideo() {
        CriarTemplateActivityVideoPermissionsDispatcher.tiraVideoWithPermissionCheck(this);
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void tiraVideo() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        File arquivoVideo = new File(caminhoVideo);
        Uri fileUri = FileProvider.getUriForFile(this, "com.mobile2.Projeto2.fileprovider", arquivoVideo);
        takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @OnClick(R.id.formulario_botao_galeria)
    public void acessarGaleria() {
        CriarTemplateActivityVideoPermissionsDispatcher.abrirGaleriaWithPermissionCheck(this);
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void abrirGaleria() {
        Intent intentGaleria = new Intent();
        // Show only images, no videos or anything else
        intentGaleria.setType("video/*");
        intentGaleria.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intentGaleria, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @SuppressLint("CheckResult")
    @OnClick(R.id.formulario_submit)
    public void salvar() {

        palavra = editText_PalavraVideo.getText().toString().toLowerCase();

        List<String> mensagens = validar();

        if (mensagens == null) {
            Intent resultado = new Intent().putExtra("template", caminhoVideoComprimido);

            GeneralRepository.saveWord(new Word(null, caminhoVideoComprimido, palavra))
                    .subscribe(() -> {
                        setResult(Activity.RESULT_OK, resultado);
                        finish();
                    }, throwable -> {
                        throwable.printStackTrace();
                        setResult(RESULT_CANCELED);
                        finish();
                    });
        } else {
            showToast(mensagens.get(0));
        }
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void onPermissionDenied() {
        showToast("Permissão necessaria.");
    }

    private void showToast(String text) {
        Toast.makeText(CriarTemplateActivityVideo.this, text, Toast.LENGTH_SHORT).show();
    }

    public List<String> validar() {
        List<String> mensagens = new ArrayList<String>();
        if (!videoAnexado) {
            mensagens.add("Um vídeo deve ser adicionado");
        }
        if (palavra.trim().length() == 0) {
            mensagens.add("Uma palavra deve ser preenchida");
        }
        if (palavra.matches("^[a-zA-Z\\u00C0-\\u00FF/]*$") == false) {
            mensagens.add("Palavra só pode conter letras");
        }

        return (mensagens.isEmpty() ? null : mensagens);
    }


    private void loadFFMpegBinary() {
        try {
            if (ffmpeg == null) {
                ffmpeg = FFmpeg.getInstance(this);
            }
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override
                public void onFailure() {
                    Log.d("VIDEO", "LOAD FAIL ");

                }

                @Override
                public void onSuccess() {
                    Log.d("VIDEO", "LOAD SUCCESS ");

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void execFFmpegBinary(final String[] command) {
        try {
            ffmpeg.execute(command, new ExecuteBinaryResponseHandler() {
                @Override
                public void onFailure(String s) {
                    Log.d("VIDEO", "FAIL: " + s);
                }

                @Override
                public void onSuccess(String s) {
                    videoCompressProgressBar.setVisibility(View.GONE);
                    mPlaceholder.setVisibility(View.GONE);
                    campoVideo.setVisibility(View.VISIBLE);
                    videoAnexado = true;
                    File videoFile = new File(caminhoVideo);
                    videoFile.delete();
                    campoVideo.setVideoURI(Uri.parse("file://" + caminhoVideoComprimido));
                    campoVideo.start();
                }

                @Override
                public void onProgress(String s) {
                    Log.d("VIDEO", "PROGRESS: " + s);

                }

                @Override
                public void onStart() {
                    Log.d("VIDEO", "STARTED ");
                    videoCompressProgressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFinish() {

                }
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        File videoFile = new File(caminhoVideoComprimido);
        if (videoFile.exists()) {
            videoFile.delete();
        }
        super.onBackPressed();
    }
}
