package br.com.servicofacil.adpter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.servicofacil.activity.ServicoLista;
import br.com.servicofacil.model.bean.Servico;
import br.com.servicofacil.servicofacil.R;

/**
 * Created by Ezequiel on 10/12/2015.
 */
public class ServicoListaAdapter extends BaseAdapter {
    //Lista de serviços
    private final List<Servico> listaDeServicos;
    //Activity responsável pela tel
    private final ServicoLista activity;
    //Injeção de dependências via construtor
    public ServicoListaAdapter(ServicoLista activity,
                               List<Servico> listaDeServicos){
        this.listaDeServicos = listaDeServicos;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return listaDeServicos.size();
    }

    @Override
    public Object getItem(int position) {
        return listaDeServicos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaDeServicos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Infla o layout na view
        View view = activity.getLayoutInflater().inflate(R.layout.itemlistagemservico, null);
        Servico servico = listaDeServicos.get(position);
        TextView nome = (TextView) view.findViewById(R.id.itemServicoLayoutNome);
        nome.setText(servico.getNomeServico());
        TextView descricao = (TextView) view.findViewById(R.id.itemServicoLayoutDescricao);
        if(descricao!=null) {
            descricao.setText(servico.getDescricao());
        }

        //Configuração da foto
        Bitmap bmp;
        if(servico.getImagem() != null){
            bmp = iconeDoServico(servico.getImagem());//BitmapFactory.decodeFile(servico.getImagem());
        } else {
            bmp = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_no_image);
        }
        bmp = Bitmap.createScaledBitmap(bmp, 100, 100, true);
        ImageView foto = (ImageView) view.findViewById(R.id.itemServicoLayoutFoto);
        foto.setImageBitmap(bmp);

        return view;
    }

    public Bitmap iconeDoServico(String nomeDaImagem){
        switch (nomeDaImagem){
            case "diarista":
                return BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_diarista);
            case "pedreiro":
                return BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_pedreiro);
            case "eletricista":
                return BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_eletricista);
            case "baba":
                return BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_baba);
            case "cuidador":
                return BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_cuidador);
            case "mecanico":
                return BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_mecanico);
            default:
                return BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_no_image);
        }

    }
}
