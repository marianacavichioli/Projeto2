package com.mobile2.projeto2.activities.criar_template;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mobile2.projeto2.R;
import com.mobile2.projeto2.entity.Word;
import com.mobile2.projeto2.repository.GeneralRepository;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CriarTemplateActivity extends Activity implements CriarTemplateView {


    @BindView(R.id.formulario_foto)
    public ImageView campoFoto;

    private CriarTemplatePresenter presenter;

    private String palavra;
    public EditText editText_Palavra;


    private boolean fotoAnexada = false;
    private static final int CODIGO_CAMERA = 123;
    public String caminhoFoto;
    private int PICK_IMAGE_REQUEST = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_template);

        ButterKnife.bind(this);
        presenter = new CriarTemplatePresenter(this);

        editText_Palavra = findViewById(R.id.editText_Palavra);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODIGO_CAMERA && resultCode == Activity.RESULT_OK) {
            fotoAnexada = true;
            exibeFoto();
        }
        else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            fotoAnexada = true;

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                escreveImagens(bitmap);
                exibeFoto();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    public void escreveImagens(Bitmap bmp){
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

            byte[] bytes = stream.toByteArray();
            caminhoFoto = Environment.getExternalStorageDirectory().getAbsolutePath() + "/image.png";

            FileOutputStream fos = new FileOutputStream(caminhoFoto);
            fos.write(bytes);
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exibeFoto(){
        Picasso.with(this)
                .load("file://"+caminhoFoto)
                .fit()
                .centerCrop()
                .into(campoFoto);
    }

    @OnClick(R.id.formulario_botao_foto)
    public void addFoto(){
        presenter.addFoto();
    }

    public void tiraFoto(){
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
        File arquivoFoto = new File(caminhoFoto);
        Uri fileUri = FileProvider.getUriForFile(this, "com.mobile2.Projeto2.fileprovider", arquivoFoto);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);
        startActivityForResult(intentCamera, CODIGO_CAMERA);

    }

    @OnClick(R.id.formulario_botao_galeria)
    public void acessarGaleria(){ presenter.acessarGaleria();}


    public void abrirGaleria() {
        Intent intentGaleria = new Intent();
        // Show only images, no videos or anything else
        intentGaleria.setType("image/*");
        intentGaleria.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intentGaleria, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @OnClick(R.id.formulario_submit)
    public void salvar(){

        palavra = editText_Palavra.getText().toString();

        List<String> mensagens = validar();

        if (mensagens == null) {
            Intent resultado = new Intent().putExtra("template",
                    presenter.getTemplate(caminhoFoto));


            String[] silabas = palavra.split("/");
            GeneralRepository.saveWord(new Word(caminhoFoto,silabas))
                    .subscribe(() -> {
                        setResult(Activity.RESULT_OK, resultado);
                        showToast(palavra);
                        finish();
                    }, throwable -> {
                        throwable.printStackTrace();
                        setResult(RESULT_CANCELED);
                        finish();
                    });


        }

        else{
            showToast(mensagens.get(0));
        }

    }



    private void showToast(String text){
        Toast.makeText(CriarTemplateActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    public List<String> validar(){
        List<String> mensagens = new ArrayList<String>();
        if (!fotoAnexada){
            mensagens.add("Uma foto deve ser adicionada");
        }
        if (palavra.trim().length() == 0) {
            mensagens.add("Uma palavra deve ser preenchida");
        }
        if (palavra.matches("^[a-zA-Z\\u00C0-\\u00FF/]*$") == false){
            mensagens.add("Palavra só pode conter letras e barras / ");
        }

        return (mensagens.isEmpty() ? null : mensagens);
    }

}
