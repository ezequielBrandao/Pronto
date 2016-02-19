package br.com.servicofacil.activity.form;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.servicofacil.helper.UsuarioDetalheHelper;
import br.com.servicofacil.helper.UsuarioHelper;
import br.com.servicofacil.model.bean.Servico;
import br.com.servicofacil.model.bean.Usuario;
import br.com.servicofacil.model.dao.UsuarioDAO;
import br.com.servicofacil.servicofacil.R;

public class UsuarioDetalheActivity extends AppCompatActivity {
    //Atributos da listagem
    private ListView lvUsuarios;
    //Coleção de usuarioes
    private List<Usuario> listaDeUsuarios;
    //usuario selecionado no click da ListView
    private Usuario usuarioSelecionado;
    //servico enviado pela lista de Servico
    private Servico servicoSelecionado;
    private UsuarioDetalheHelper helper;
    private Usuario usuarioLogin;

    //Path para o rquivo de foto do usuario
    private String localArquivoFoto;
    //Constante usada como requestCode
    private static final int FAZER_FOTO_USUARIO = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_detalhe);
        Servico servico = (Servico)getIntent().getSerializableExtra("SERVICO_SELECIONADO");
        definirToolbar(servico);
        Usuario usuario = (Usuario)getIntent().getSerializableExtra("USUARIO_SELECIONADO");
        usuarioLogin = (Usuario)getIntent().getSerializableExtra("USUARIO_LOGADO");

        helper = new UsuarioDetalheHelper(this);

        //Verifica se é necessário atualizar a tela com dados do usuario
        if(usuarioLogin!=null){
            Toast.makeText(UsuarioDetalheActivity.this, "Usuário logado: "+usuarioLogin.getNome(), Toast.LENGTH_SHORT).show();
//            helper.setUsuario(usuario);
        }

        TextView text = (TextView) findViewById(R.id.itemUsuarioLayoutNome);
        text.setText(usuario.getNome()+" " + usuario.getDataNascimento()+" "+ usuario.getEndereco());
        //Preparar para consultar o Banco
        RatingBar ratingBar = (RatingBar)findViewById(R.id.avaliacao);
        if (usuario.getAvaliacao()!=null){
            ratingBar.setRating(usuario.getAvaliacao().floatValue());
        }
        else ratingBar.setRating(3);

        TextView text2 = (TextView) findViewById(R.id.itemUsuarioLayoutDescricao);
        text2.setText(usuario.getCompetencias());

        Bitmap bmp;
        if(usuario.getFoto() !=null){
            bmp = BitmapFactory.decodeFile(usuario.getFoto());
        }else{
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_no_image);
        }
        bmp = Bitmap.createScaledBitmap(bmp, 100, 100, true);
        ImageView foto = (ImageView) findViewById(R.id.itemUsuarioLayoutFoto);
        foto.setImageBitmap(bmp);
        //Configuração de click da imagem
        /*helper.getFoto().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localArquivoFoto = Environment.getExternalStorageDirectory()
                        + "/" + System.currentTimeMillis() + ".jpg";
                File arquivo = new File(localArquivoFoto);
                Uri localFoto = Uri.fromFile(arquivo);
                Intent irParaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                irParaCamera.putExtra(MediaStore.EXTRA_OUTPUT, localFoto);
                startActivityForResult(irParaCamera, FAZER_FOTO_USUARIO);
            }
        });*/

        /*btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario usuario = helper.getUsuario();

                UsuarioDAO dao = new UsuarioDAO(UsuarioForm.this);
                if(usuario.getId()==null){
                    dao.cadastrar(usuario);
                }else{
                    dao.alterar(usuario);
                }
                dao.close();
                //limpa campos do formulario
                //helper.setUsuario(new Usuario());
                //Fecha a tela atual
                finish();
            }
        });*/

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


    private void definirToolbar(Servico servico){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        switch (servico.getImagem()){
            case "diarista":
                toolbar.setTitle("Diaristas");
                toolbar.setBackgroundColor(this.getResources().getColor(R.color.colorDirista));
                break;
            case "pedreiro":
                toolbar.setTitle("PEDREIRO");
                toolbar.setBackgroundColor(this.getResources().getColor(R.color.colorPedreiro));
                break;
            case "eletricista":
                toolbar.setTitle("Eletricista");
                toolbar.setBackgroundColor(this.getResources().getColor(R.color.colorEletricista));
                break;
            case "baba":
                toolbar.setTitle("Babá");
                toolbar.setBackgroundColor(this.getResources().getColor(R.color.colorBaba));
                break;
            case "cuidador":
                toolbar.setTitle("Cuidador de idosos");
                toolbar.setBackgroundColor(this.getResources().getColor(R.color.colorCuidador));
                break;
            case "mecanico":
                toolbar.setTitle("Mecânico");
                toolbar.setBackgroundColor(this.getResources().getColor(R.color.colorMecanico));
                break;
            default:
                toolbar.setTitle("Lista de Profissionais ");
        }
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_usuario_lista, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //recupera o id do item do menu selecionado
        int id = item.getItemId();

        //Click no botão para cadastro de usuarioes
        switch (id){
            case  R.id.menuItemMapa:
                Toast.makeText(this, "O item mapa foi clicado", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuItemNovo:
                startActivity(new Intent(this, UsuarioForm.class));
//                Toast.makeText(this, "O item Novo foi clicado", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuItemEnviar:
                Toast.makeText(this, "O item enviar foi clicado", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuItemReceber:
                Toast.makeText(this, "O item receber foi clicado", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuItemPreferencias:
                Toast.makeText(this, "O item preferencias foi clicado", Toast.LENGTH_SHORT).show(); 
            default:
                return super.onOptionsItemSelected(item);
        }
        /*if (id == R.id.menuItemNovo){
            Toast.makeText(this, "O item Novo foi clicado", Toast.LENGTH_SHORT).show();
            return true;
        }*/
//        return super.onOptionsItemSelected(item);
    }



}
