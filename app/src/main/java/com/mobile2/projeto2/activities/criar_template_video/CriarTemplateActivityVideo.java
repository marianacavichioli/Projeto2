package com.mobile2.projeto2.activities.criar_template_video.;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mobile2.projeto2.R;
import com.mobile2.projeto2.activities.criar_template_video.CriarTemplateVideoPresenter;
import com.mobile2.projeto2.activities.criar_template_video.CriarTemplateVideoView;
import com.mobile2.projeto2.entity.Word;
import com.mobile2.projeto2.repository.GeneralRepository;
import com.squareup.picasso.Picasso;

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


    @BindView(R.id.formulario_video)
    public ImageView campoVideo;

    private CriarTemplateVideoPresenter presenter;

    private String palavra;
    public EditText editText_PalavraVideo;


    private boolean videoAnexado = false;
    private static final int CODIGO_CAMERA = 123;
    public String caminhoVideo;
    private int PICK_IMAGE_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_template_video);

        ButterKnife.bind(this);
        presenter = new CriarTemplateVideoPresenter(this);

        editText_PalavraVideo = findViewById(R.id.editText_PalavraVideo);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODIGO_CAMERA && resultCode == Activity.RESULT_OK) {
            videoAnexado = true;
            CriarTemplateActivityVideoPermissionsDispatcher.exibeVideoWithPermissionCheck(this);
        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            videoAnexado = true;

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                CriarTemplateActivityVideoPermissionsDispatcher.escreveImagensWithPermissionCheck(this, bitmap);
                CriarTemplateActivityVideoPermissionsDispatcher.exibeVideoWithPermissionCheck(this);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    public void escreveVideo(Bitmap bmp) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

            caminhoVideo = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + UUID.randomUUID() + ".png";

            FileOutputStream fos = new FileOutputStream(caminhoVideo);
            fos.write(stream.toByteArray());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE})
    public void exibeVideo() {
        Picasso.with(this)
                .load("file://" + caminhoVideo)
                .fit()
                .centerCrop()
                .into(campoVideo);
    }

    @OnClick(R.id.formulario_botao_video)
    public void addVideo() {
        CriarTemplateActivityVideoPermissionsDispatcher.tiraVideoWithPermissionCheck(this);
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
    public void tiraVideo() {
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        caminhoVideo = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
        File arquivoVideo = new File(caminhoVideo);
        Uri fileUri = FileProvider.getUriForFile(this, "com.mobile2.Projeto2.fileprovider", arquivoVideo);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intentCamera, CODIGO_CAMERA);
    }

    @OnClick(R.id.formulario_botao_galeria)
    public void acessarGaleria() {
        CriarTemplateActivityVideoPermissionsDispatcher.abrirGaleriaWithPermissionCheck(this);
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void abrirGaleria() {
        Intent intentGaleria = new Intent();
        // Show only images, no videos or anything else
        intentGaleria.setType("image/*");
        intentGaleria.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intentGaleria, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @SuppressLint("CheckResult")
    @OnClick(R.id.formulario_submit)
    public void salvar() {

        palavra = editText_PalavraVideo.getText().toString();

        List<String> mensagens = validar();

        if (mensagens == null) {
            Intent resultado = new Intent().putExtra("template",
                    presenter.getTemplate(caminhoVideo));


            String[] silabas = palavra.split("/");
            GeneralRepository.saveWord(new Word(caminhoVideo, silabas))
                    .subscribe(() -> {
                        setResult(Activity.RESULT_OK, resultado);
                        showToast(palavra);
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
    public void onPermissionDenied(){
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
        if (palavra.matches("^[a-zA-Z\\u00C0-\\u00FF]*$") == false) {
            mensagens.add("Palavra só pode conter letras");
        }

        return (mensagens.isEmpty() ? null : mensagens);
    }

}
