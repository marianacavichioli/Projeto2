package com.mobile2.projeto2.criar_template;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.mobile2.projeto2.R;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CriarTemplateActivity extends AppCompatActivity implements CriarTemplateView {

    @BindView(R.id.formulario_maximo)
    public EditText campoMaximo;
    @BindView(R.id.formulario_minimo)
    public EditText campoMinimo;
    @BindView(R.id.formulario_foto)
    public ImageView campoFoto;

    private CriarTemplatePresenter presenter;

    private static final int CODIGO_CAMERA = 123;
    public String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_template);

        ButterKnife.bind(this);
        presenter = new CriarTemplatePresenter(this);

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
    public void tiraFoto(){
        Log.d("VOU TIRAR FOTO","TIRANDO");
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
        File arquivoFoto = new File(caminhoFoto);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
        startActivityForResult(intentCamera, CODIGO_CAMERA);
    }

    @OnClick(R.id.formulario_submit)
    public void salvar(){
        Intent resultado = new Intent().putExtra("template",
                presenter.getTemplate(campoMaximo.getText().toString(),
                        campoMinimo.getText().toString(),
                        caminhoFoto));

        setResult(Activity.RESULT_OK, resultado);
        finish();

    }

}
