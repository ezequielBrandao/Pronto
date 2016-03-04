package br.com.servicofacil.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.servicofacil.activity.form.UsuarioDetalheActivity;
import br.com.servicofacil.activity.form.UsuarioForm;
import br.com.servicofacil.adpter.UsuarioProfissionalListaAdapter;
import br.com.servicofacil.model.bean.Servico;
import br.com.servicofacil.model.bean.Usuario;
import br.com.servicofacil.model.dao.ComentarioDAO;
import br.com.servicofacil.model.dao.ServicoDAO;
import br.com.servicofacil.model.dao.UsuarioDAO;
import br.com.servicofacil.servicofacil.R;

public class UsuarioList extends AppCompatActivity {
    //Atributos da listagem
    private ListView lvUsuarios;
    //Coleção de usuarioes
    private List<Usuario> listaDeUsuarios;
    //usuario selecionado no click da ListView
    private Usuario usuarioSelecionado;
    //servico enviado pela lista de Servico
    private Servico servicoSelecionado;
    //Usuario de Login
    private Usuario usuarioLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_list);
        //Usuario logado
        usuarioLogin = (Usuario)getIntent().getSerializableExtra("USUARIO_LOGADO");
        Servico servico = (Servico)getIntent().getSerializableExtra("SERVICO_SELECIONADO");
        definirToolbar(servico);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        //bind da ListView
        lvUsuarios = (ListView) findViewById(R.id.lvListagem);
        //chamada para carga de dados
        registerForContextMenu(lvUsuarios);
        if(servico==null){
            carregarLista();
        }
        else{
            carregarListaUsuarioProfissional(servico);
        }

    }
    private void definirToolbar(Servico servico){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        switch (servico.getImagem()){
            case "diarista":
                toolbar.setTitle("Diaristas");
                toolbar.setBackgroundColor(UsuarioList.this.getResources().getColor(R.color.colorDirista));
                break;
            case "pedreiro":
                toolbar.setTitle("PEDREIRO");
                toolbar.setBackgroundColor(UsuarioList.this.getResources().getColor(R.color.colorPedreiro));
                break;
            case "eletricista":
                toolbar.setTitle("Eletricista");
                toolbar.setBackgroundColor(UsuarioList.this.getResources().getColor(R.color.colorEletricista));
                break;
            case "baba":
                toolbar.setTitle("Babá");
                toolbar.setBackgroundColor(UsuarioList.this.getResources().getColor(R.color.colorBaba));
                break;
            case "cuidador":
                toolbar.setTitle("Cuidador de idosos");
                toolbar.setBackgroundColor(UsuarioList.this.getResources().getColor(R.color.colorCuidador));
                break;
            case "mecanico":
                toolbar.setTitle("Mecânico");
                toolbar.setBackgroundColor(UsuarioList.this.getResources().getColor(R.color.colorMecanico));
                break;
            default:
                toolbar.setTitle("Lista de Profissionais ");
        }
        setSupportActionBar(toolbar);
    }

    private void carregarListaUsuarioProfissional(Servico servico){
        //Componente que converte String em View
        UsuarioProfissionalListaAdapter adapter;
        //Carregar lista a partir  do banco de dados
        UsuarioDAO dao = new UsuarioDAO(this);
        listaDeUsuarios = dao.listarProfissionais(servico);
        dao.close();
        if(listaDeUsuarios==null){
            Usuario usuario = new Usuario();
            usuario.setId(1l);
            usuario.setNome("Albermar Junior");
            usuario.setAvaliacao(5.0);
            listaDeUsuarios.add(usuario);
        }

        //Associação do adapter ao listView
        configurarClickDaListagem(servico);

        //Criação do adaptador customizado
        adapter = new UsuarioProfissionalListaAdapter(this, listaDeUsuarios);
        //Associação do adapter ao listView
        lvUsuarios.setAdapter(adapter);
    }

    private void carregarLista(){
        //Componente que converte String em View
        UsuarioProfissionalListaAdapter adapter;
        //Carregar lista a partir  do banco de dados
        UsuarioDAO dao = new UsuarioDAO(this);
        listaDeUsuarios = dao.listar();
        dao.close();
        if(listaDeUsuarios==null){
            Usuario usuario = new Usuario();
            usuario.setId(1l);
            usuario.setNome("Albermar Junior");
            usuario.setAvaliacao(5.0);
            listaDeUsuarios.add(usuario);
        }

        //Associação do adapter ao listView
        configurarClickDaListagem();

        //Criação do adaptador customizado
        adapter = new UsuarioProfissionalListaAdapter(this, listaDeUsuarios);
        //Associação do adapter ao listView
        lvUsuarios.setAdapter(adapter);
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

    @Override
    protected void onResume() {
        Servico servico = (Servico)getIntent().getSerializableExtra("SERVICO_SELECIONADO");
        if(servico==null){
            carregarLista();
        }
        else{
            carregarListaUsuarioProfissional(servico);
        }
        super.onResume();
    }

    private void configurarClickDaListagem(final Servico servico){
        //configurção do evento de click curto
        lvUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Pega a referência para o usuario selecionado
                usuarioSelecionado = listaDeUsuarios.get(position);
//                usuarioSelecionado.setServico(servico);
                Intent form = new Intent(UsuarioList.this, UsuarioDetalheActivity.class);
                //Passagem de parâmetros
                form.putExtra("USUARIO_SELECIONADO", usuarioSelecionado);
                form.putExtra("SERVICO_SELECIONADO", servico);
                form.putExtra("USUARIO_LOGADO", usuarioLogin);
                //iniciar a nova activity
                startActivity(form);
//                Toast.makeText(UsuarioLista.this, "[Click simples] " + listaDeUsuarios.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configurarClickDaListagem(){
        //configurção do evento de click curto
        lvUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Pega a referência para o usuario selecionado
                usuarioSelecionado = listaDeUsuarios.get(position);
                Intent form = new Intent(UsuarioList.this, UsuarioForm.class);
                //Passagem de parâmetros
                form.putExtra("USUARIO_SELECIONADO", usuarioSelecionado);
                //iniciar a nova activity
                startActivity(form);
//                Toast.makeText(UsuarioLista.this, "[Click simples] " + listaDeUsuarios.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        //configuração do evento de click longo
        lvUsuarios.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //pega a referencia para o usuario selecionado
                usuarioSelecionado = listaDeUsuarios.get(position);
                //Toast.makeText(UsuarioLista.this, "[Click longo] " + listaDeUsuarios.get(position), Toast.LENGTH_SHORT).show();
                //Return false para não consumir o click curto
                //e exibir o menu de contexto
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_usuario_contexto, menu);
    }

    /*Chamado quando um item do menu de contexto for selecionado
    * @param item Objeto selecionado
    * @return false para permitir o processamento do item clicado*/
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.itemMenuContextoExcluir:
                excluir();
                break;
            case R.id.itemMenuContextoLigar:
                //Intent implicito para chamada telefônica
                intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + usuarioSelecionado.getTelefone()));
                try{
                    startActivity(intent);
                }catch (SecurityException e){
                    Toast.makeText(this, "Ligação não habilitada", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.itemMenuContextoEnviarSMS:
                intent = new Intent(Intent.ACTION_VIEW);
                //Marcação para envio de SMS
                intent.setData(Uri.parse("sms:" + usuarioSelecionado.getTelefone()));
                intent.putExtra("sms_body", "Mensagem de boasvindas :-)");
                startActivity(intent);
                break;
            case R.id.itemMenuContextoVerNoMapa:
                intent = new Intent(Intent.ACTION_VIEW);
                //Marcação para exibição de mapa
                intent.setData(Uri.parse("geo:0,0?z=14&q=" + usuarioSelecionado.getCep()));
                startActivity(intent);
                break;
            /*case R.id.itemMenuContextoNavegarSite:
                intent = new Intent(Intent.ACTION_VIEW);
                //marcação para navvegação na web
                intent.setData(Uri.parse("http:" + usuarioSelecionado.getSite()));
                startActivity(intent);
                break;*/
            case R.id.itemMenuContextoEnviarEmail:
                intent = new Intent(Intent.ACTION_SEND);
                //marcação para envio de email
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{usuarioSelecionado.getEmail()});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Falando sobre o curso");
                intent.putExtra(Intent.EXTRA_TEXT, "O curso foi muito legal");
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void excluir(){
        //Criação do omponente de confirmação de exclusão
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Configuração da mensagem
        builder.setMessage("Confirma a exclusão de: "+usuarioSelecionado.getNome()+"?");
        //Configuração do botão de confirmação de exclusão
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UsuarioDAO dao = new UsuarioDAO(UsuarioList.this);
                //Execução do método de exclusão
                dao.excluir(usuarioSelecionado);
                dao.close();
                //Atualização da listagem
                carregarLista();
                usuarioSelecionado=null;
            }
        });
        //Configuração da ação de cancelamento da exclusão
        builder.setNegativeButton("Não", null);
        //Criação da caixa de dialogo
        AlertDialog dialog = builder.create();
        dialog.setTitle("Confirmação de operação");
        //Exibição da caixa de dialogo
        dialog.show();
    }
}