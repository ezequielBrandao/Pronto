package br.com.servicofacil.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import br.com.servicofacil.activity.form.UsuarioForm;
import br.com.servicofacil.model.bean.Usuario;
import br.com.servicofacil.servicofacil.R;

/**
 * Created by Ezequiel on 11/11/2015.
 */
public class UsuarioHelper {
    private Usuario usuario;

    private EditText nome;
    private EditText cep;
    private EditText cpf;
    private EditText endereco;
    private EditText nascimento;
    private EditText senha;
    private EditText email;
    private EditText telefone;
    private EditText disponibilidade;
    private ImageView foto;
    private String localDaFoto;
    private EditText competencias;
    private EditText servico;
    private RadioGroup tipoUsuario;

    public UsuarioHelper(UsuarioForm formulario){
        nome = (EditText) formulario.findViewById(R.id.edNome);
        cep = (EditText) formulario.findViewById(R.id.edCep);
        cpf = (EditText) formulario.findViewById(R.id.edCpf);
        endereco = (EditText) formulario.findViewById(R.id.edEndereco);
        nascimento = (EditText) formulario.findViewById(R.id.edDataNascimento);
        senha = (EditText) formulario.findViewById(R.id.edSenha);
        email = (EditText) formulario.findViewById(R.id.edEmail);
        telefone = (EditText) formulario.findViewById(R.id.edTelefone);
        disponibilidade = (EditText) formulario.findViewById(R.id.edDisponibilidade);
        foto = (ImageView) formulario.findViewById(R.id.foto);
        competencias = (EditText) formulario.findViewById(R.id.edCompetencia);
        servico = (EditText) formulario.findViewById(R.id.edServico);
        tipoUsuario = (RadioGroup) formulario.findViewById(R.id.radioTipoUsuario);

        usuario = new Usuario();
    }

    public Usuario getUsuario(){
        usuario.setNome(nome.getText().toString());
        usuario.setCep(cep.getText().toString());
        usuario.setCpf(cpf.getText().toString());
        usuario.setEndereco(endereco.getText().toString());
        usuario.setDataNascimento(nascimento.getText().toString());
        usuario.setEmail(email.getText().toString());
        usuario.setSenha(senha.getText().toString());
        usuario.setTelefone(telefone.getText().toString());
        usuario.setDisponibilidade(disponibilidade.getText().toString());
        usuario.setFoto(localDaFoto);
        usuario.setCompetencias(competencias.getText().toString());
        usuario.setServico(servico.getText().toString());
        if (tipoUsuario.getCheckedRadioButtonId()==R.id.tipoProfissional){
            usuario.setTipoUsuario(true);
        }
        else usuario.setTipoUsuario(false);


        return usuario;
    }

    public void setUsuario(Usuario usuario){
        nome.setText(usuario.getNome());
        cep.setText(usuario.getCep());
        cpf.setText(usuario.getCpf());
        endereco.setText(usuario.getEndereco());
        nascimento.setText(usuario.getDataNascimento());
        email.setText(usuario.getEmail());
        senha.setText(usuario.getSenha());
        telefone.setText(usuario.getTelefone());
        disponibilidade.setText(usuario.getDisponibilidade());
        competencias.setText(usuario.getCompetencias());
        servico.setText(usuario.getServico());
        if(usuario.getTipoUsuario()){
            tipoUsuario.check(R.id.tipoProfissional);
        }
        else tipoUsuario.check(R.id.tipoUsuario);

        this.usuario = usuario;
        //Chamada ao método que atualiza a imagem
        if(usuario.getFoto()!=null){
            carregarFoto(usuario.getFoto());
            this.localDaFoto=usuario.getFoto();
        }
    }

    public ImageView getFoto() {
        return foto;
    }

    /**
     * Método que carrega uma foto, a partir de um arquivo
     * e armazena no device
     */
    public void carregarFoto(String localArquivoFoto){
        Log.d("Usuario_HELPER", "Local Arquivo: " + localArquivoFoto);
        // Carregar arquivo de imagem
        Bitmap imagemFoto = BitmapFactory.decodeFile(localArquivoFoto);
        //Gerar imagem reduzida
        Bitmap imagemFotoReduzida = Bitmap.createScaledBitmap(imagemFoto, 100, 100, true);
        //Atualiza a imagem exibida na tela de formulário
        foto.setImageBitmap(imagemFotoReduzida);
        localDaFoto=localArquivoFoto;
    }
}
