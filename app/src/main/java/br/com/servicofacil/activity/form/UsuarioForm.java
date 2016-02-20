package br.com.servicofacil.activity.form;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

import br.com.servicofacil.helper.UsuarioHelper;
import br.com.servicofacil.model.bean.Usuario;
import br.com.servicofacil.model.dao.UsuarioDAO;
import br.com.servicofacil.servicofacil.R;

public class UsuarioForm extends AppCompatActivity {

    private UsuarioHelper helper;
    private Button btSalvar;

    //Path para o rquivo de foto do usuario
    private String localArquivoFoto;
    //Constante usada como requestCode
    private static final int FAZER_FOTO_USUARIO = 12345;

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
                Usuario usuario = helper.getUsuario();

                //Criação do Objeto Dao e inicio da conexção com o BD
                UsuarioDAO dao = new UsuarioDAO(UsuarioForm.this);
                if(usuario.getId()==null){
                    usuario.setAvaliacao(5.0);
                    //Chamada do método de cadastro de usuario
                    dao.cadastrar(usuario);
                }else{
                    usuario.setAvaliacao(3.0);
                    //Atualiza os dados do usuario
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
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
}
