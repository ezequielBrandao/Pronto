package br.com.servicofacil.adpter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import br.com.servicofacil.activity.UsuarioList;
import br.com.servicofacil.model.bean.Usuario;
import br.com.servicofacil.servicofacil.R;

/**
 * Created by Ezequiel on 19/12/2015.
 */
public class UsuarioProfissionalListaAdapter extends BaseAdapter {
    //lista de Usuarios Profissionais
    private final List<Usuario> listadeUsuarioProfissionais;
    //Activity responsável pela tela
    private final UsuarioList activity;
    //Injeção de dependências via construtor
    public UsuarioProfissionalListaAdapter(UsuarioList activity,
                                           List<Usuario> listaDeUsuariosProfissionais){
        this.listadeUsuarioProfissionais = listaDeUsuariosProfissionais;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listadeUsuarioProfissionais.size();
    }

    @Override
    public Object getItem(int position) {
        return listadeUsuarioProfissionais.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listadeUsuarioProfissionais.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Infla o layout na view
        View view = activity.getLayoutInflater().inflate(R.layout.itemlistagemusuario, null);
        Usuario usuario = listadeUsuarioProfissionais.get(position);
        //Configuração do Nome do usuario
        TextView nome = (TextView) view.findViewById(R.id.usuarioNome);
        nome.setText(usuario.getNome());
        //Configuração do ranking de usuario
        RatingBar avaliacao = (RatingBar) view.findViewById(R.id.usuarioRanking);
        avaliacao.setRating(usuario.getAvaliacao().floatValue());
        //Configuração de idade
        TextView idadeAprox = (TextView) view.findViewById(R.id.usuarioIdade);
        idadeAprox.setText(usuario.getDataNascimento()+" - ");
        //Configuração de Cidade e Bairro do profissional
        TextView cidadeBairro = (TextView) view.findViewById(R.id.usuarioCidadeBairro);
        cidadeBairro.setText(usuario.getEndereco());

        //Configuração da foto
        Bitmap bmp;
        if(usuario.getFoto() !=null){
            bmp = BitmapFactory.decodeFile(usuario.getFoto());
        }else{
            bmp = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_no_image);
        }
        bmp = Bitmap.createScaledBitmap(bmp, 100, 100, true);
        ImageView foto = (ImageView) view.findViewById(R.id.usuarioFoto);
        foto.setImageBitmap(bmp);

        return view;
    }
}