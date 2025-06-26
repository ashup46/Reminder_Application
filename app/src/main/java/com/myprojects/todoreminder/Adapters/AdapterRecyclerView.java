package com.myprojects.todoreminder.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myprojects.todoreminder.Modal.TaskListModal;
import com.myprojects.todoreminder.R;
import com.myprojects.todoreminder.RoomDatabaase.ModalReminder;

import java.util.List;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.holderView> {

    Context context;

    List<ModalReminder> taskList;

    public AdapterRecyclerView(Context context, List<ModalReminder> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public AdapterRecyclerView.holderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

         View view = LayoutInflater.from(context).inflate(R.layout.modal_task_list,parent,false);

         holderView hv = new holderView(view);


        return hv;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerView.holderView holder, int position) {

        holder.getTaskName().setText(taskList.get(position).getReminderNotes());
        holder.getTaskTime().setText(taskList.get(position).getTime());
        holder.getTaskWeek().setText(taskList.get(position).getWeeks());





    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class holderView extends RecyclerView.ViewHolder {

      private   TextView taskName,taskTime,taskWeek;
        public holderView(@NonNull View itemView) {
            super(itemView);

            taskName = itemView.findViewById(R.id.taskHeadingId);
            taskTime = itemView.findViewById(R.id.taskTimeId);
            taskWeek = itemView.findViewById(R.id.taskWeekId);

        }

        public TextView getTaskName() {
            return taskName;
        }

        public TextView getTaskTime() {
            return taskTime;
        }

        public TextView getTaskWeek() {
            return taskWeek;
        }
    }
}
