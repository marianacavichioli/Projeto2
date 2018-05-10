package com.mobile2.projeto2.criar_template;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mobile2.projeto2.MainActivity;
import com.mobile2.projeto2.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CriarTemplateActivity extends AppCompatActivity implements CriarTemplateView {


    @BindView(R.id.formulario_foto)
    public ImageView campoFoto;

    private CriarTemplatePresenter presenter;

    private String palavra;
    public EditText editText_Palavra;


    private boolean fotoAnexada = false;
    private static final int CODIGO_CAMERA = 123;
    public String caminhoFoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_template);

        ButterKnife.bind(this);
        presenter = new CriarTemplatePresenter(this);

        editText_Palavra = findViewById(R.id.editText_Palavra);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODIGO_CAMERA && resultCode == Activity.RESULT_OK) {
            fotoAnexada = true;
            exibeFoto();
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
        Log.d("VOU TIRAR FOTO","TIRANDO");
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
        File arquivoFoto = new File(caminhoFoto);
        Uri fileUri = FileProvider.getUriForFile(this, "com.mobile2.Projeto2.fileprovider", arquivoFoto);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);
        startActivityForResult(intentCamera, CODIGO_CAMERA);

    }

    @OnClick(R.id.formulario_submit)
    public void salvar(){

        palavra = editText_Palavra.getText().toString();

        List<String> mensagens = validar();

        if (mensagens == null) {
            Intent resultado = new Intent().putExtra("template",
                    presenter.getTemplate(caminhoFoto));

            setResult(Activity.RESULT_OK, resultado);


            showToast(palavra);
            finish();
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
