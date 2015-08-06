package np.com.rowsun.mykcmit;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by rowsun on 8/3/15.
 */
public class viewAdapter extends RecyclerView.Adapter<viewAdapter.MyViewHolder>{

    private Context con;
    private LayoutInflater inflater;
    List<navigationClass> data= Collections.emptyList();


    public viewAdapter(Context context,List<navigationClass> data)
    {   con=context;
        inflater=LayoutInflater.from(context);
        this.data=data;
    }

    public void delete(int position)
    {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.custom_row,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,int position) {
        navigationClass current=data.get(position);
        final int pos=position;
        holder.title.setText(current.itemName);
        holder.image.setImageResource(current.itemId);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView title;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.itemText);
            image= (ImageView) itemView.findViewById(R.id.itemIcon );
            image.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        delete(getPosition());
        }
    }
}
