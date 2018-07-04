package com.mobile2.projeto2.activities.criar_template;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile2.projeto2.R;
import com.mobile2.projeto2.entity.Syllable;
import com.mobile2.projeto2.entity.Word;
import com.mobile2.projeto2.repository.GeneralRepository;
import com.mobile2.projeto2.util.SyllableList;
import com.mobile2.projeto2.util.Toaster;
import com.squareup.picasso.Picasso;

import java.io.File;
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
public class CriarTemplateActivity extends AppCompatActivity implements CriarTemplateInterface.View, AdapterView.OnItemClickListener {


    @BindView(R.id.formulario_foto)
    public ImageView campoFoto;
    @BindView(R.id.editText_Palavra)
    public AutoCompleteTextView syllableInput;

    private CriarTemplateInterface.Presenter presenter;
    private List<String> mWordSyllables = new ArrayList<>();
    private String palavra;


    private boolean fotoAnexada = false;
    private static final int CODIGO_CAMERA = 123;
    public String caminhoFoto =  Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + UUID.randomUUID() + ".jpg";
    private int PICK_IMAGE_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_template);

        ButterKnife.bind(this);
        presenter = new CriarTemplatePresenter(this);
        presenter.getSyllables();
        syllableInput.setOnItemClickListener(this);

    }

    @Override
    public void setSyllableList(List<String> syllableList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, syllableList);
        syllableInput.setAdapter(adapter);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CODIGO_CAMERA && resultCode == Activity.RESULT_OK) {
            fotoAnexada = true;
            exibeFoto();

        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            fotoAnexada = true;
            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                CriarTemplateActivityPermissionsDispatcher.escreveImagensWithPermissionCheck(this, bitmap);
                exibeFoto();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    public void escreveImagens(Bitmap bmp) {
        presenter.copyImageToAppFolder(bmp,caminhoFoto);
    }

    public void exibeFoto() {
        Picasso.with(this)
                .load(Uri.parse("file://" + caminhoFoto))
                .fit()
                .centerCrop()
                .into(campoFoto);
    }

    @OnClick(R.id.formulario_botao_foto)
    public void addFoto() {
        CriarTemplateActivityPermissionsDispatcher.tiraFotoWithPermissionCheck(this);
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void tiraFoto() {
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File arquivoFoto = new File(caminhoFoto);
        Uri fileUri = FileProvider.getUriForFile(this, "com.mobile2.Projeto2.fileprovider", arquivoFoto);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        if (intentCamera.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intentCamera, CODIGO_CAMERA);
        }
    }

    @OnClick(R.id.formulario_botao_galeria)
    public void acessarGaleria() {
        CriarTemplateActivityPermissionsDispatcher.abrirGaleriaWithPermissionCheck(this);
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void abrirGaleria() {
        Intent intentGaleria = new Intent();
        intentGaleria.setType("image/*");
        intentGaleria.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intentGaleria, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    @SuppressLint("CheckResult")
    @OnClick(R.id.formulario_submit)
    public void salvar() {

        List<String> mensagens = presenter.validateFields(fotoAnexada, mWordSyllables);

        if (mensagens == null) {
            Word word = new Word(caminhoFoto, null, mWordSyllables.toArray(new String[0]));
            presenter.saveWord(word);
        } else {
            showToast(mensagens.get(0));
        }
    }

    @Override
    public void onWordSaved() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void onError(String errorMessage) {
        setResult(RESULT_CANCELED);
        finish();
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void onPermissionDenied(){
        showToast("Permissão necessaria.");
    }

    private void showToast(String text) {
        Toast.makeText(CriarTemplateActivity.this, text, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final TextView textView = (TextView) view;
        final String syllable = textView.getText().toString();
        syllableInput.getEditableText().clear();

        if (!syllable.matches("^[a-zA-Z\\u00C0-\\u00FF]*$")) {
            showToast("Sílaba só pode conter letras");
            return;
        }
        mWordSyllables.add(syllable);
    }
}
