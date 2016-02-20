package br.com.servicofacil.activity;

import android.content.Intent;
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

import br.com.servicofacil.activity.form.UsuarioForm;
import br.com.servicofacil.adpter.ServicoListaAdapter;
import br.com.servicofacil.model.bean.Servico;
import br.com.servicofacil.model.bean.Usuario;
import br.com.servicofacil.model.dao.ServicoDAO;
import br.com.servicofacil.model.dao.UsuarioDAO;
import br.com.servicofacil.servicofacil.R;

public class ServicoLista extends AppCompatActivity {
    //Atributo da listagem
    private ListView lvServicos;
    //Coleção de Serviços
    private List<Servico> listaDeServicos;
    //Serviço Selecionado
    private Servico servicoSelecionado;
    //Usuario recebido no login
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servico_lista);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setPopupTheme(R.style.AppTheme_Baba);
        setSupportActionBar(toolbar);
        UsuarioDAO dao = new UsuarioDAO(this);
        usuario = dao.loginUsuario(false);
        Toast.makeText(ServicoLista.this, "Usuario "+usuario.getNome()+ "logado com sucesso! Tipo: "+usuario.getTipoUsuario(), Toast.LENGTH_SHORT).show();
        //Bind da ListView
        lvServicos = (ListView) findViewById(R.id.lvListagem);
        registerForContextMenu(lvServicos);
        //Chamada para carga dos dados
        carregarLista();
        lvServicos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Pega a referência para o professor selecionado
                servicoSelecionado = listaDeServicos.get(position);
                Intent list = new Intent(ServicoLista.this, UsuarioList.class);
                //passa por parâetro
                list.putExtra("SERVICO_SELECIONADO", servicoSelecionado);
                //passa usuario logado
                list.putExtra("USUARIO_LOGADO", usuario);
                //Inicia a activity
                startActivity(list);
            }
        });

    }

    private void carregarLista(){
        //Componente que converte Servicos em Views
        ServicoListaAdapter adapter;
        //carregar lista  partir do banco de dados mas....
        //não vamos usar o banco de dados nesse momento. Vamos fazer a mão
//        List<Servico> servicos = new ArrayList<Servico>();
        listaDeServicos = new ArrayList<Servico>();
        Servico servico = new Servico();
        servico.setId(1l);
        servico.setNomeServico("Diarista");
        servico.setImagem("diarista");//"\\servicofacil\\app\\src\\main\\res\\drawable\\icone_diarista// .png");
        servico.setDescricao("Precisando de uma limpeza?");
        Servico servico2 = new Servico();
        servico2.setId(2l);
        servico2.setNomeServico("Pedreiro");
        servico2.setImagem("pedreiro");
        servico2.setDescricao("Querendo uma reforma?");
        Servico servico3 = new Servico();
        servico3.setId(3l);
        servico3.setNomeServico("Eletricista");
        servico3.setImagem("eletricista");
        servico3.setDescricao("Deu curto?");
        Servico servico4 = new Servico();
        servico4.setId(4l);
        servico4.setNomeServico("Babá");
        servico4.setImagem("baba");
        servico4.setDescricao("Precisando de uma mãe temporária?");
        Servico servico5 = new Servico();
        servico5.setId(5l);
        servico5.setNomeServico("Cuidador de Idosos");
        servico5.setImagem("cuidador");
        servico5.setDescricao("Alguém atenciosa e carinhosa?");
        Servico servico6 = new Servico();
        servico6.setId(6l);
        servico6.setNomeServico("Mecânico");
        servico6.setImagem("mecanico");
        servico6.setDescricao("O carro deu problema?");
        listaDeServicos.add(servico);
        listaDeServicos.add(servico2);
        listaDeServicos.add(servico3);
        listaDeServicos.add(servico4);
        listaDeServicos.add(servico5);
        listaDeServicos.add(servico6);

        //Criação do adaptador customizado
        adapter = new ServicoListaAdapter(this, listaDeServicos);
        //Associação do adapter ao listView
        lvServicos.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_servico_lista, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        //Recupera o id do item do menu selecionado
        int id = item.getItemId();

        //Click no botão para cadastro de professores
        if(id==R.id.cadastrar){
            intent = new Intent(this, UsuarioForm.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
