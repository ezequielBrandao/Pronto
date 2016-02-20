package br.com.servicofacil.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import br.com.servicofacil.model.bean.Servico;
import br.com.servicofacil.model.bean.Usuario;

/**
 * Created by Ezequiel on 27/11/2015.
 */
public class UsuarioDAO extends SQLiteOpenHelper {

    private static int VERSAO = 1;
    private static String TABELA = "Usuario";
    private static String BANCO_DE_DADOS = "Pronto";

    private static String TAG_LOGCAT = "USUARIO-DAO";

    public UsuarioDAO(Context context){
        super(context, BANCO_DE_DADOS, null, VERSAO);
        Log.i(TAG_LOGCAT, "Método construtor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddl = "CREATE TABLE "+ TABELA+" (" +
                " id INTEGER PRIMARY KEY, " +
                " nome TEXT, cep TEXT, cpf TEXT, " +
                " endereco TEXT, nascimento TEXT, " +
                " senha TEXT, email TEXT, telefone TEXT, " +
                " disponibilidade TEXT, foto TEXT, competencias TEXT, " +
                " avaliacao DECIMAL, servico TEXT, tipo_usuario BOOLEAN)";
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

    public void cadastrar(Usuario usuario){

        //Objeto para armazenar os valores dos campos
        ContentValues values = new ContentValues();

        //Definição dos valores dos campos da tabela
        values.put("nome", usuario.getNome());
        values.put("cep", usuario.getCep());
        values.put("cpf", usuario.getCpf());
        values.put("endereco", usuario.getEndereco());
        values.put("nascimento", usuario.getDataNascimento());
        values.put("senha", usuario.getSenha());
        values.put("email", usuario.getEmail());
        values.put("telefone", usuario.getTelefone());
        values.put("disponibilidade", usuario.getDisponibilidade());
        values.put("foto", usuario.getFoto());
        values.put("competencias", usuario.getCompetencias());
        values.put("avaliacao", usuario.getAvaliacao());
        if (usuario.getServico()!=null)
            values.put("servico", usuario.getServico());
        values.put("tipo_usuario", usuario.getTipoUsuario());

        //inserir dados do usuario
        getWritableDatabase().insert(TABELA, null, values);
        Log.i(TAG_LOGCAT, "Usuario "+ usuario.getNome()+" cadastrado com sucesso!");

    }

    public List<Usuario> listar() {
        //Definição de coleção de usuarioes
        List<Usuario> lista = new ArrayList<>();

        //Definição da instrução SQL
        String sql = "Select * from " + TABELA + " order by nome";

        //objeto que recebe os registros do banco de dados (write para escrever, nesse caso queremos ler)
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        try {
            //Percorre os registros do banco de dados
            while (cursor.moveToNext()) {
                //Criação de nova referencia para Usuario
                Usuario usuario = new Usuario();

                //Servico servico = new Servico();

                //Carrega os atributos de usuario com os campos da tabela
                usuario.setId(cursor.getLong(0));
                usuario.setNome(cursor.getString(1));
                usuario.setCep(cursor.getString(2));
                usuario.setCpf(cursor.getString(3));
                usuario.setEndereco(cursor.getString(4));
                usuario.setDataNascimento(cursor.getString(5));
                usuario.setEmail(cursor.getString(7));
                usuario.setDisponibilidade(cursor.getString(9));
                usuario.setFoto(cursor.getString(10));
                usuario.setCompetencias(cursor.getString(11));
                usuario.setAvaliacao(cursor.getDouble(12));
                //servico.setId(cursor.getLong(13));
                //Deve fazer uma busca pelo ID do serviço
                //usuario.setServico(servico);
                usuario.setId(cursor.getLong(13));
                usuario.setTipoUsuario(cursor.getInt(14) == 1);

                //Adiciona o novo usuario a lista
                lista.add(usuario);
            }
        } catch (SQLException e) {
            Log.e(TAG_LOGCAT, e.getMessage());
        } finally {
            //fecha a lista de registros do banco de dados
            cursor.close();
        }
        return lista;
    }

    public void excluir(Usuario usuario){
        //Definição de array de parametros
        String[]args = { usuario.getId().toString()};

        //Exclusão do usuario
        getWritableDatabase().delete(TABELA, "id=?", args);
        Log.i(TAG_LOGCAT, "Usuario Excluído: " + usuario.getNome());
    }

    /**Chamado para atualização de dados do usuario
     * @param usuario O usuario a ser atualizado
     * */
    public void alterar(Usuario usuario){
        ContentValues values = new ContentValues();
        values.put("nome", usuario.getNome());
        values.put("cep", usuario.getCep());
        values.put("cpf", usuario.getCpf());
        values.put("endereco", usuario.getEndereco());
        values.put("nascimento", usuario.getDataNascimento());
        values.put("senha", usuario.getSenha());
        values.put("email", usuario.getEmail());
        values.put("telefone", usuario.getTelefone());
        values.put("disponibilidade", usuario.getDisponibilidade());
        values.put("foto", usuario.getFoto());
        values.put("competencias", usuario.getCompetencias());
        values.put("avaliacao", usuario.getAvaliacao());
        if (usuario.getServico()!=null)
            values.put("servico", usuario.getServico());
        values.put("tipo_usuario", usuario.getTipoUsuario());

        //Coleção de valores de parametros do SQL
        String[] args = { usuario.getId().toString()};

        //Altera dados do Usuario no BD
        getWritableDatabase().update(TABELA, values, "id=?", args);
        Log.i(TAG_LOGCAT, "Usuario "+usuario.getNome()+" alterado com sucesso!");
    }

    public List<Usuario> listarProfissionais(Servico servico) {
        //Definição de coleção de usuarioes
        List<Usuario> lista = new ArrayList<>();

        //Definição da instrução SQL
        String sql = "Select * from " + TABELA + " where servico = '"+servico.getImagem()+"'";

        //objeto que recebe os registros do banco de dados (write para escrever, nesse caso queremos ler)
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        try {
            //Percorre os registros do banco de dados
            while (cursor.moveToNext()) {
                //Criação de nova referencia para Usuario
                Usuario usuario = new Usuario();

                //Servico serv = new Servico();

                //Carrega os atributos de usuario com os campos da tabela
                usuario.setId(cursor.getLong(0));
                usuario.setNome(cursor.getString(1));
                usuario.setCep(cursor.getString(2));
                usuario.setCpf(cursor.getString(3));
                usuario.setEndereco(cursor.getString(4));
                usuario.setDataNascimento(cursor.getString(5));
                usuario.setEmail(cursor.getString(7));
                usuario.setDisponibilidade(cursor.getString(9));
                usuario.setFoto(cursor.getString(10));
                usuario.setCompetencias(cursor.getString(11));
                usuario.setAvaliacao(cursor.getDouble(12));
                //servico.setId(cursor.getLong(13));
                //Deve fazer uma busca pelo ID do serviço
                //usuario.setServico(servico);
                usuario.setServico(cursor.getString(13));
                usuario.setTipoUsuario(cursor.getInt(14) == 1);

                //Adiciona o novo usuario a lista
                lista.add(usuario);
            }
        } catch (SQLException e) {
            Log.e(TAG_LOGCAT, e.getMessage());
        } finally {
            //fecha a lista de registros do banco de dados
            cursor.close();
        }
        return lista;
    }

    public Usuario loginUsuario(Boolean tipoUsuario){
        int find;
        if(tipoUsuario==false)
            find=0;
        else find=1;

            //Definição da instrução SQL
            String sql = "Select * from " + TABELA + " where tipo_usuario = "+find;

            //objeto que recebe os registros do banco de dados (write para escrever, nesse caso queremos ler)
            Cursor cursor = getReadableDatabase().rawQuery(sql, null);
            try {
                //Percorre os registros do banco de dados
                while (cursor.moveToNext()) {
                    //Criação de nova referencia para Usuario
                    Usuario usuario = new Usuario();

                    //Carrega os atributos de usuario com os campos da tabela
                    usuario.setId(cursor.getLong(0));
                    usuario.setNome(cursor.getString(1));
                    usuario.setCep(cursor.getString(2));
                    usuario.setCpf(cursor.getString(3));
                    usuario.setEndereco(cursor.getString(4));
                    usuario.setDataNascimento(cursor.getString(5));
                    usuario.setEmail(cursor.getString(7));
                    usuario.setDisponibilidade(cursor.getString(9));
                    usuario.setFoto(cursor.getString(10));
                    usuario.setCompetencias(cursor.getString(11));
                    usuario.setAvaliacao(cursor.getDouble(12));
                    //servico.setId(cursor.getLong(13));
                    //Deve fazer uma busca pelo ID do serviço
                    //usuario.setServico(servico);
                    usuario.setServico(cursor.getString(13));
                    usuario.setTipoUsuario(cursor.getInt(14) == 1);

                    //Adiciona o novo usuario a lista
                    if (usuario.getId() !=null) return usuario;
                }
            } catch (SQLException e) {
                Log.e(TAG_LOGCAT, e.getMessage());
            } finally {
                //fecha a lista de registros do banco de dados
                cursor.close();
            }
        /*Usuario usuario = new Usuario();
        usuario.setId(8761l);
        usuario.setNome("Cristiane Ronaldiane");
        usuario.setTipoUsuario(false);
        this.cadastrar(usuario);*/
        return null;

    }
}
