package com.example.architecturecomp.View.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.architecturecomp.R;
import com.example.architecturecomp.View.Database.MainData;
import com.example.architecturecomp.View.Database.RoomDB;
import com.example.architecturecomp.View.MainActivity;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.RowHolder> {
    private List<MainData> dataList;
    private RoomDB roomDB;
    private Context context;
    private MainActivity mainActivity;

    public MainAdapter(Context context, List<MainData> dataList, MainActivity mainActivity) {
        this.context = context;
        this.dataList = dataList;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_row_main, parent, false);
        return new RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {
        MainData data = dataList.get(position);
        roomDB = RoomDB.getInstance(context);
        holder.textView.setText(data.getText());
        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog1 = setDialog();
                dialog1.show();
                MainData data1 = dataList.get(position);
                int sID = data1.getId();
                String sText = data1.getText();
                EditText editText = dialog1.findViewById(R.id.edit_Text);
                Button btUpdate = dialog1.findViewById(R.id.bt_update);
                editText.setText(sText);
                btUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();
                        String updatedText = editText.getText().toString();
                        roomDB.maindao().update(sID, updatedText);
                        dataList.clear();
                        dataList.addAll(roomDB.maindao().getData());
                        notifyDataSetChanged();
                    }
                });
            }
        });
        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainData mainData = dataList.get(position);
                roomDB.maindao().delete(mainData);
                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, dataList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class RowHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView btEdit, btDelete;

        public RowHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
            btDelete = itemView.findViewById(R.id.delete_image);
            btEdit = itemView.findViewById(R.id.edit_image);
        }
    }

    public Dialog setDialog() {
        Dialog dialog = new Dialog(mainActivity);
        dialog.setContentView(R.layout.dialog_layout);
        int width = WindowManager.LayoutParams.MATCH_PARENT;
        int height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);
        return dialog;
    }
}
