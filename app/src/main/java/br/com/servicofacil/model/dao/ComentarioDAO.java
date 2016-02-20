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
import br.com.servicofacil.model.bean.Servico;
import br.com.servicofacil.model.bean.Usuario;

/**
 * Created by Ezequiel on 27/11/2015.
 */
public class ComentarioDAO extends SQLiteOpenHelper {

    private static int VERSAO = 1;
    private static String TABELA = "Comentario";
    private static String BANCO_DE_DADOS = "Pronto";

    private static String TAG_LOGCAT = "COMENTARIO-DAO";

    public ComentarioDAO(Context context){
        super(context, BANCO_DE_DADOS, null, VERSAO);
        Log.i(TAG_LOGCAT, "Método construtor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddl = "CREATE TABLE "+ TABELA+" (" +
                " id INTEGER PRIMARY KEY, " +
                " id_comentador LONG, id_comentado LONG, comentario TEXT)";
        //Execução do comanddo SQL
        db.execSQL(ddl);
        Log.i(TAG_LOGCAT, "Criação da tabela: "+TABELA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Instrução DDL para destruir a tabela
        String ddl ="DROP TABLE IF EXISTS "+TABELA;
        //Execução da instrução no SQLite
        db.execSQL(ddl);
        Log.i(TAG_LOGCAT, "Atualização da versão da tabela: "+TABELA);
    }

    public void cadastrar(Comentario comentario){

        //Objeto para armazenar os valores dos campos
        ContentValues values = new ContentValues();

        //Definição dos valores dos campos da tabela
        values.put("id_comentador", comentario.getUsuarioComentando().getId());
        values.put("id_comentado", comentario.getUsuarioComentado().getId());
        values.put("comentario", comentario.getComentario());

        //inserir dados do usuario
        getWritableDatabase().insert(TABELA, null, values);
        Log.i(TAG_LOGCAT, "Comentario "+ comentario.getComentario()+" cadastrado com sucesso!");

    }

    public List<Comentario> listarComentado(Long idComentado) {
        //Definição de coleção de usuarioes
        List<Comentario> lista = new ArrayList<>();

        //Definição da instrução SQL
        String sql = "Select * from " + TABELA + " where id_comentado='"+idComentado+"'";

        //objeto que recebe os registros do banco de dados (write para escrever, nesse caso queremos ler)
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        try {
            //Percorre os registros do banco de dados
            while (cursor.moveToNext()) {
                //Criação de nova referencia para Usuario
                Comentario comentario = new Comentario();
                Usuario comentado = new Usuario();
                Usuario comentador = new Usuario();

                //Servico servico = new Servico();

                //Carrega os atributos de usuario com os campos da tabela
                comentario.setId(cursor.getLong(0));
                comentador.setId(cursor.getLong(1));
                comentario.setUsuarioComentando(comentador);
                comentado.setId(cursor.getLong(2));
                comentario.setUsuarioComentando(comentado);
                comentario.setComentario(cursor.getString(3));

                //Adiciona o novo usuario a lista
                lista.add(comentario);
            }
        } catch (SQLException e) {
            Log.e(TAG_LOGCAT, e.getMessage());
        } finally {
            //fecha a lista de registros do banco de dados
            cursor.close();
        }
        return lista;
    }

    public void excluir(Comentario comentario){
        //Definição de array de parametros
        String[]args = { comentario.getId().toString()};

        //Exclusão do usuario
        getWritableDatabase().delete(TABELA, "id=?", args);
        Log.i(TAG_LOGCAT, "Comentario Excluído: " + comentario.getComentario());
    }

    /**Chamado para atualização de dados do usuario
     * @param comentario O usuario a ser atualizado
     * */
    public void alterar(Comentario comentario){
        ContentValues values = new ContentValues();
        values.put("id_comentador", comentario.getUsuarioComentando().getId());
        values.put("id_comentado", comentario.getUsuarioComentado().getId());
        values.put("comentario", comentario.getComentario());

        //Coleção de valores de parametros do SQL
        String[] args = { comentario.getId().toString()};

        //Altera dados do Usuario no BD
        getWritableDatabase().update(TABELA, values, "id=?", args);
        Log.i(TAG_LOGCAT, "Comentario "+comentario.getComentario()+" alterado com sucesso!");
    }

}
