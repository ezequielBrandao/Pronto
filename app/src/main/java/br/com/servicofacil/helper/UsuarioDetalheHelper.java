package br.com.servicofacil.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import br.com.servicofacil.activity.form.UsuarioDetalheActivity;
import br.com.servicofacil.model.bean.Usuario;
import br.com.servicofacil.servicofacil.R;

/**
 * Created by Ezequiel on 17/12/2015.
 */
public class UsuarioDetalheHelper {
    private Usuario usuario;
    private EditText nome;
    private EditText cep;
    private EditText cpf;
    private EditText senha;
    private EditText email;
    private EditText competencias;
    private EditText telefone;
    private EditText profissao;
    private EditText disponibilidade;
    private ImageView foto;
    private String localDaFoto;

    public UsuarioDetalheHelper(UsuarioDetalheActivity formulario) {
        nome = (EditText) formulario.findViewById(R.id.edNome);
        cep = (EditText) formulario.findViewById(R.id.edCep);
        cpf = (EditText) formulario.findViewById(R.id.edCpf);
        email = (EditText) formulario.findViewById(R.id.edEmail);
        senha = (EditText) formulario.findViewById(R.id.edSenha);
        competencias = (EditText) formulario.findViewById(R.id.edCompetencia);
        telefone = (EditText) formulario.findViewById(R.id.edTelefone);
        profissao = (EditText) formulario.findViewById(R.id.edServico);
        disponibilidade = (EditText) formulario.findViewById(R.id.edDisponibilidade);
        foto = (ImageView) formulario.findViewById(R.id.foto);

        usuario = new Usuario();
    }

    public Usuario getUsuario() {
        usuario.setNome(nome.getText().toString());
        usuario.setCep(cep.getText().toString());
        usuario.setCpf(cpf.getText().toString());
        usuario.setEmail(email.getText().toString());
        usuario.setSenha(senha.getText().toString());
        usuario.setCompetencias(competencias.getText().toString());
        usuario.setTelefone(telefone.getText().toString());
        usuario.setServico(profissao.getText().toString());
        usuario.setDisponibilidade(disponibilidade.getText().toString());
        usuario.setFoto(localDaFoto);

        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        nome.setText(usuario.getNome());
        cep.setText(usuario.getCep());
        cpf.setText(usuario.getCpf());
        email.setText(usuario.getEmail());
        senha.setText(usuario.getSenha());
        telefone.setText(usuario.getTelefone());
        competencias.setText(usuario.getCompetencias());
        profissao.setText(usuario.getServico());
        disponibilidade.setText(usuario.getDisponibilidade());
        this.usuario = usuario;
        //Chamada ao método que atualiza a imagem
        if (usuario.getFoto() != null) {
            carregarFoto(usuario.getFoto());
            this.localDaFoto = usuario.getFoto();
        }
    }

    public ImageView getFoto() {
        return foto;
    }

    /**
     * Método que carrega uma foto, a partir de um arquivo
     * e armazena no device
     */
    public void carregarFoto(String localArquivoFoto) {
        Log.d("Usuario_HELPER", "Local Arquivo: " + localArquivoFoto);
        // Carregar arquivo de imagem
        Bitmap imagemFoto = BitmapFactory.decodeFile(localArquivoFoto);
        //Gerar imagem reduzida
        Bitmap imagemFotoReduzida = Bitmap.createScaledBitmap(imagemFoto, 100, 100, true);
        //Atualiza a imagem exibida na tela de formulário
        foto.setImageBitmap(imagemFotoReduzida);
        localDaFoto = localArquivoFoto;
    }
}