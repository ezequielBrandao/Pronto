package br.com.servicofacil.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.servicofacil.model.bean.Servico;

/**
 * Created by Ezequiel on 27/11/2015.
 */
public class ServicoDAO extends SQLiteOpenHelper{

    //Constantes para o controle de versão e padronização
    private static final int VERSAO = 1;
    private static final String TABELA = "Servico";
    private static final String BANCO_DE_DADOS = "Pronto";

    //Constante para tag do LOGCAT
    private static final String TAG_LOGCAT= "SERVICO-DAO";

    public ServicoDAO(Context context){
        //Chamada ao construtor que sabe acessar o BD
        super(context, BANCO_DE_DADOS, null, VERSAO);
        Log.i(TAG_LOGCAT, "método construtor");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Instrução DDL a ser executada
        String ddl = "CREATE TABLE "+TABELA+" ( " +
                " id INTEGER PRIMARY KEY, " +
                " nomeServico TEXT, descricao TEXT, imagem TEXT)";
        //Execução da instrução no SQLite
        db.execSQL(ddl);
        Log.i(TAG_LOGCAT, "Criação da tabela: " + TABELA);
        //cargaInicial();
    }

    /**
     * Chamado quando o BD e incrementado
     * verificar melhorias nesse código*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Instrução DDL para destruir a tabela
        String ddl = "DROP TABLE IF EXISTS "+TABELA;
        //Execução da instrução no SQLite
        db.execSQL(ddl);
        //Chamada do metodo de reconstrução da tabela
        onCreate(db);
        Log.i(TAG_LOGCAT, "Atualização da versão da tabela: " + TABELA);
    }

    /**
     * Chamado para dar carga inicial nos serviços já pré cadastrados*/
    public void cargaInicial(){
        //Objeto para armazenar os valores dos campos
        ContentValues values = new ContentValues();
        ContentValues values2 = new ContentValues();

        //Definição de valores dos campos da tabela
        values.put("nomeServico", "Diarista");
        values.put("descricao", "Fazer faxina em residencias ou empresas");

        //Inserir daddos do Professor no BD
        getWritableDatabase().insert(TABELA, null, values);


        Log.i(TAG_LOGCAT, "Carga inicial efetuada!");

    }

    public List<Servico> listar(){
        //Definição da coleção de serviços
        List<Servico> lista = new ArrayList<>();

        //Definição da instrução SQL
        String sql = "Select * from " + TABELA + " order by nomeServico";

        //Objeto que recebe os registros do banco de dados
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        try {
            //Percorre os registros do banco de dados
            while (cursor.moveToNext()){
                //Criação de nova referencia para Servico
                Servico servico = new Servico();

                //Carregar os atributos de Servico com os campos da tabela
                servico.setId(cursor.getLong(0));
                servico.setNomeServico(cursor.getString(1));
                servico.setDescricao(cursor.getString(2));
                servico.setImagem(cursor.getString(3));
                //Adiciona na lista de serviços
                lista.add(servico);
            }
        } catch (SQLException e){
            Log.e(TAG_LOGCAT, e.getMessage());
        } finally{
            //Fecha a lista de registros do BD
            cursor.close();
        }
        return  lista;
    }
}