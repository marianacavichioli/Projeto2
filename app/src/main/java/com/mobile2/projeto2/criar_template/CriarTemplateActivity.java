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

    private String palavra, silabaCorreta, silabaIncorreta1, silabaIncorreta2;
    public EditText editText_Palavra, editText_SilabaCorreta, editText_SilabaIncorreta1, editText_SilabaIncorreta2;



    private static final int CODIGO_CAMERA = 123;
    public String caminhoFoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_template);

        ButterKnife.bind(this);
        presenter = new CriarTemplatePresenter(this);

        editText_Palavra = findViewById(R.id.editText_Palavra);
        editText_SilabaCorreta = findViewById(R.id.editText_SilabaCorreta);
        editText_SilabaIncorreta1 = findViewById(R.id.editText_SilabaIncorreta1);
        editText_SilabaIncorreta2 = findViewById(R.id.editText_SilabaIncorreta2);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODIGO_CAMERA && resultCode == Activity.RESULT_OK) {
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
        silabaCorreta = editText_SilabaCorreta.getText().toString();
        silabaIncorreta1 = editText_SilabaIncorreta1.getText().toString();
        silabaIncorreta2 = editText_SilabaIncorreta2.getText().toString();

        List<String> mensagens = validar();

        if (mensagens == null) {
            Intent resultado = new Intent().putExtra("template",
                    presenter.getTemplate(caminhoFoto));

            setResult(Activity.RESULT_OK, resultado);




            showToast(palavra);
            finish();
        }

        else{
            showErro(mensagens);
        }

    }

    private void showToast(String text){
        Toast.makeText(CriarTemplateActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    private void showErro(List<String> erro) {
        Toast.makeText(CriarTemplateActivity.this, erro.get(0), Toast.LENGTH_SHORT).show();
    }

    public List<String> validar(){
        List<String> mensagens = new ArrayList<String>();
        if (palavra.trim().length() == 0) {
            mensagens.add("O primeiro campo deve ser preenchido");
        }
        if (silabaCorreta.trim().length() == 0) {
            mensagens.add("O segundo campo deve ser preenchido");
        }
        if (silabaIncorreta1.trim().length() == 0) {
            mensagens.add("O terceiro campo deve ser preenchido");
        }
        if (silabaIncorreta2.trim().length() == 0) {
            mensagens.add("O quarto campo deve ser preenchido");
        }

        return (mensagens.isEmpty() ? null : mensagens);
    }

}
