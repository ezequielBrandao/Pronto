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

import br.com.servicofacil.model.bean.Comentario;
import br.com.servicofacil.model.bean.Favorito;
import br.com.servicofacil.model.bean.Servico;
import br.com.servicofacil.model.bean.Usuario;

/**
 * Created by Ezequiel on 27/11/2015.
 */
public class FavoritoDAO extends SQLiteOpenHelper{

    //Constantes para o controle de versão e padronização
    private static final int VERSAO = 1;
    private static final String TABELA = "favorito";
    private static final String BANCO_DE_DADOS = "Pronto";

    //Constante para tag do LOGCAT
    private static final String TAG_LOGCAT= "SERVICO-DAO";

    public FavoritoDAO(Context context){
        //Chamada ao construtor que sabe acessar o BD
        super(context, BANCO_DE_DADOS, null, VERSAO);
        Log.i(TAG_LOGCAT, "método construtor");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Instrução DDL a ser executada
        String ddl = "CREATE TABLE "+TABELA+" ( " +
                " id INTEGER PRIMARY KEY, " +
                "id_usuario INTEGER," +
                "id_favorito INTEGER)";
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

    public void cadastrar(Favorito favorito){

        //Objeto para armazenar os valores dos campos
        ContentValues values = new ContentValues();

        //Definição dos valores dos campos da tabela
        values.put("id_usuario", favorito.getUsuario().getId());
        values.put("id_favorito", favorito.getUsuarioFavoritado().getId());

        //inserir dados do usuario
        getWritableDatabase().insert(TABELA, null, values);
        Log.i(TAG_LOGCAT, "Comentario "+ favorito.getId()+ " cadastrado com sucesso!");

    }

    /**
     * Chamado para dar carga inicial nos serviços já pré cadastrados*/
      public List<Favorito> listarComentado(Long idUsuario) {
        //Definição de coleção de usuarioes
        List<Favorito> lista = new ArrayList<>();

        //Definição da instrução SQL
        String sql = "Select f.id, u.* from favorito f join usario u on u.id = f.id_usuario where ='"+idUsuario+"'";

        //objeto que recebe os registros do banco de dados (write para escrever, nesse caso queremos ler)
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        try {
            //Percorre os registros do banco de dados
            while (cursor.moveToNext()) {
                //Criação de nova referencia para Usuario
                Favorito favorito = new Favorito();
                Usuario usuario = new Usuario();
                Usuario usuarioFavoritado = new Usuario();

                //Servico servico = new Servico();

                //Carrega os atributos de usuario com os campos da tabela
                favorito.setId(cursor.getLong(0));
                /*usuarioFavoritado.setId(cursor.getLong(1));
                favorito.setUsuarioComentando(usuarioFavoritado);
                usuario.setId(cursor.getLong(2));
                favorito.setUsuarioComentando(usuario);
                favorito.setComentario(cursor.getString(3));*/

                //Adiciona o novo usuario a lista
                lista.add(favorito);
            }
        } catch (SQLException e) {
            Log.e(TAG_LOGCAT, e.getMessage());
        } finally {
            //fecha a lista de registros do banco de dados
            cursor.close();
        }
        return lista;
    }

}