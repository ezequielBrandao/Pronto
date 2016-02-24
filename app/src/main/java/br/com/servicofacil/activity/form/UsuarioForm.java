package br.com.servicofacil.activity.form;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;

import br.com.servicofacil.helper.UsuarioHelper;
import br.com.servicofacil.model.bean.Usuario;
import br.com.servicofacil.model.dao.UsuarioDAO;
import br.com.servicofacil.servicofacil.R;
import br.com.servicofacil.util.ValidadorDeCampos;

public class UsuarioForm extends AppCompatActivity {

    private UsuarioHelper helper;
    private Button btSalvar;
    private EditText nome;
    private EditText cpf;
    private EditText senha;
    private Spinner servico;
    private RadioGroup tipoUsuario;


    //Path para o rquivo de foto do usuario
    private String localArquivoFoto;
    //Constante usada como requestCode
    private static final int FAZER_FOTO_USUARIO = 12345;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_form);

        Log.d("CICLO DE VIDA", "Executou o metodo onCreate()");

        helper = new UsuarioHelper(this);
        //Recupera o usuario passado como parâmetro
        Usuario usuario = (Usuario)getIntent().getSerializableExtra("USUARIO_SELECIONADO");
        //Verifica se é necessário atualizar a tela com dados do usuario
        if(usuario!=null){
            helper.setUsuario(usuario);
        }
        layout = (LinearLayout) findViewById(R.id.form_usuario);
        layout.setVisibility(View.INVISIBLE);



        btSalvar = (Button) findViewById(R.id.btSalvar);

        //Configuração de click da imagem
        helper.getFoto().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localArquivoFoto = Environment.getExternalStorageDirectory()
                        + "/" + System.currentTimeMillis() + ".jpg";
                File arquivo = new File(localArquivoFoto);
                //URI que informa onde o arquivo resultado deve ser salvo
                Uri localFoto = Uri.fromFile(arquivo);
                Intent irParaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                irParaCamera.putExtra(MediaStore.EXTRA_OUTPUT, localFoto);
                startActivityForResult(irParaCamera, FAZER_FOTO_USUARIO);
            }
        });

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nome = (EditText) findViewById(R.id.edNome);
                cpf = (EditText) findViewById(R.id.edCpf);
                senha = (EditText) findViewById(R.id.edSenha);
                servico= (Spinner) findViewById(R.id.spnProfissoes);
                tipoUsuario = (RadioGroup) findViewById(R.id.radioTipoUsuario);

                Usuario usuario = helper.getUsuario();

                //Criação do Objeto Dao e inicio da conexção com o BD
                UsuarioDAO dao = new UsuarioDAO(UsuarioForm.this);
                if(validarCampos()){
                    if(usuario.getId()==null){
                        dao.cadastrar(usuario);
                    }else{
                        dao.alterar(usuario);
                    }
                    //encerra a conexão com o BD
                    dao.close();
                    //Feedback do usuário
                    Toast.makeText(UsuarioForm.this, "Usuario(a) salvo(a): " + usuario.getNome(), Toast.LENGTH_LONG).show();

                    //limpa campos do formulario
                    //helper.setUsuario(new Usuario());
                    //Fecha a tela atual
                    finish();

                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Spinner spinner = (Spinner) findViewById(R.id.spnProfissoes);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.profissoes_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

    }

    private boolean validarCampos() {
        boolean cpfPreenchido = ValidadorDeCampos.validateNotNull(cpf, "Preencha o campo CPF");

        if (!cpfPreenchido) {
            return false;
        }

        boolean cpfValido = ValidadorDeCampos.validateCPF(cpf.getText().toString());

        if (!cpfValido) {
            cpf.setError("CPF inválido");
            cpf.setFocusable(true);
            cpf.requestFocus();
            return false;
        }


        boolean nomePreenchida = ValidadorDeCampos.validateNotNull(nome, "Preencha o campo Nome");

        if (!nomePreenchida) {
            return false;
        }

        boolean senhaPreenchida = ValidadorDeCampos.validateNotNull(senha, "Preencha o campo Senha");

        if (!senhaPreenchida) {
            return false;
        }

        boolean tipoUsuarioSelecionado = ValidadorDeCampos.validateNotNull(tipoUsuario, "Selecione o Tipo de Usuário");

        if (!tipoUsuarioSelecionado) {
            return false;
        }

        if (tipoUsuario.getCheckedRadioButtonId() == R.id.tipoProfissional) {
            boolean profissaoSelecionada = ValidadorDeCampos.validateNotNull(servico, "Selecione o Tipo de Usuário");

            if (!profissaoSelecionada) {
                return false;
            }

        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_usuario_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == FAZER_FOTO_USUARIO){
            if (resultCode == Activity.RESULT_OK){
                helper.carregarFoto(localArquivoFoto);
            }else{
                localArquivoFoto = null;
            }
        }
    }

    public void verificaProfissional(View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.tipoProfissional:
                if (checked)
                    layout.setVisibility(View.VISIBLE);
                    break;
            case R.id.tipoUsuario:
                if (checked)
                    layout.setVisibility(View.INVISIBLE);
                break;
        }

    }


}
