package com.example.silento;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.ArrayList;

/**
 * Created by vishank on 29/2/16.
 */
public class recycler_adapter extends RecyclerSwipeAdapter<recycler_adapter.ProfileViewHolder> {

    private Context context;
    private ArrayList<ProfilesList> arrayList;

    DataBaseManipulator dataBaseManipulator;

    /*public recycler_adapter(Context context) {
        this.context = context;
    }
*/
    public recycler_adapter(Context context, ArrayList<ProfilesList> arrayList, DisplayMetrics metrics)
    {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public ProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.swipe_custom_single_row, parent, false);
        ProfileViewHolder viewHolder = new ProfileViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ProfileViewHolder viewHolder, final int position) {


        viewHolder.profileTextView.setText(arrayList.get(position).getProfileName());

        viewHolder.from_till_TextView.setText(arrayList.get(position).getStartHour() + ":"
                + arrayList.get(position).getStartMinute() + " - "
                + arrayList.get(position).getEndHour() + ":"
                + arrayList.get(position).getEndMinute() + "");

        if (arrayList.get(position).getStatus() == 0)
            viewHolder.enableImageView.setImageResource(R.mipmap.ic_action_toggle_radio_button_off);
        else
            viewHolder.enableImageView.setImageResource(R.mipmap.ic_action_radio_button_on);

        if (arrayList.get(position).getstart_profileType().equals("Silent"))
            viewHolder.start_profileTypeImageView.setImageResource(R.mipmap.ic_action_silent);
        else if (arrayList.get(position).getstart_profileType().equals("Vibration"))
            viewHolder.start_profileTypeImageView.setImageResource(R.mipmap.ic_action_shake_me);
        else
            viewHolder.start_profileTypeImageView.setImageResource(R.mipmap.ic_action_normal);


        if (arrayList.get(position).getend_profileType().equals("Silent"))
            viewHolder.end_profileTypeImageView.setImageResource(R.mipmap.ic_action_silent);
        else if (arrayList.get(position).getend_profileType().equals("Vibration"))
            viewHolder.end_profileTypeImageView.setImageResource(R.mipmap.ic_action_shake_me);
        else
            viewHolder.end_profileTypeImageView.setImageResource(R.mipmap.ic_action_normal);

       getRepaetDays(viewHolder, arrayList , position);






        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.right_wrapper));
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, null);
        mItemManger.bindView(viewHolder.itemView, position);

//        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
//            @Override
//            public void onStartOpen(SwipeLayout layout) {
//
//            }
//
//            @Override
//            public void onOpen(SwipeLayout layout) {
//
//            }
//
//            @Override
//            public void onStartClose(SwipeLayout layout) {
//
//            }
//
//            @Override
//            public void onClose(SwipeLayout layout) {
//
//            }
//
//            @Override
//            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
//                boolean opened = SwipeLayout.Status.Open.equals(layout.getOpenStatus());
//                if (opened && leftOffset > -450)
//                    layout.close(true);
//
//            }
//
//            @Override
//            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
//
//            }
//        });
//
//        viewHolder.swipeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean opened = SwipeLayout.Status.Open.equals(viewHolder.swipeLayout.getOpenStatus());
//
//                if (!opened)
//                    viewHolder.swipeLayout.open(true);
//                else
//                    viewHolder.swipeLayout.close(true);
//            }
//        });


        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                viewHolder.swipeLayout.close(true);
                //Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();

                Intent updateIntent = new Intent(context, updateAlarmActivity.class);
                updateIntent.putExtra("position", position);
                //id = getIdCursor.getInt(0);

                //updateIntent.putExtra("id" , getIdCursor.getInt(0));
                context.startActivity(updateIntent);
                ((AlarmList) context).overridePendingTransition(R.anim.slide_in_left, 0);






            }
        });

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.swipeLayout.close(true);
                Toast.makeText(context, "Delete " + position  , Toast.LENGTH_SHORT).show();


                String alarmTitle_delete = arrayList.get(position).getProfileName();


              /*  dataBaseManipulator = new DataBaseManipulator(context);
                Cursor delete_alarm = dataBaseManipulator.fetchalarms();
                delete_alarm.moveToPosition(position);
                alarmId_delete = delete_alarm.getInt(0);
                alarmTitle_delete = delete_alarm.getString(6);*/

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Wait!!");

                String first = "Delete the Event ";
                String next = "<font color='#E91E63'>" + alarmTitle_delete + "</font>";
                String last = " ?";
                //t.setText(Html.fromHtml(first + next));

                builder.setMessage(Html.fromHtml(first + next + last));

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.cancel();

                    }
                });

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                        dataBaseManipulator = new DataBaseManipulator(context);
                        dataBaseManipulator.deleteAlarm(arrayList.get(position).getId());
                        dataBaseManipulator.close();

                        arrayList.remove(position);

                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, arrayList.size());
                        ((AlarmList) context).setEmptyView(arrayList.size());
                        //customListAdapter.remove(profile);
                        //Toast.makeText(AlarmList.this, "Event "+alarmTitle_delete+" deleted", Toast.LENGTH_SHORT).show();
                        //initializeListView();
                        Intent i = new Intent(context, MyAlarm.class);
                        i.setAction("setAlarm");
                        context.startService(i);


                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                /*delete_alarm.close();
                dataBaseManipulator.close();*/

            }
        });
    }




    private void getRepaetDays(ProfileViewHolder holder, ArrayList<ProfilesList> profileParcel2, int position) {
        String check;
        check = "prob";
        CharSequence initial = "When it's";

        if (arrayList.get(position).getSun() == 1)
            initial = initial + " Sunday";

        if (arrayList.get(position).getMon() == 1) {


            if (arrayList.get(position).getSun() == 1)
                check = ",";
            else if(arrayList.get(position).getSun() == 0)
                check ="bl";

            if (arrayList.get(position).getTue() == 0 && arrayList.get(position).getWed() == 0
                    && arrayList.get(position).getThur() == 0 && arrayList.get(position).getFri() == 0
                    && arrayList.get(position).getSat() == 0)
                check = "or";

            switch (check)
            {
                case ",": initial = initial + ", Monday";
                    break;
                case "bl": initial = initial + " Monday";
                    break;
                case "or": initial = initial + " or Monday";
                    break;
                default: initial = " problem ";

            }
        }

        if (arrayList.get(position).getTue() == 1) {



            if (arrayList.get(position).getSun() != 1 && arrayList.get(position).getMon()!=1)
                check = "bl";
            else
                check =",";

            if (arrayList.get(position).getWed() == 0
                    && arrayList.get(position).getThur() == 0 && arrayList.get(position).getFri() == 0
                    && arrayList.get(position).getSat() == 0)
                check = "or";

            switch (check)
            {
                case ",": initial = initial + ", Tuesday";
                    break;
                case "bl": initial = initial + " Tuesday";
                    break;
                case "or": initial = initial + " or Tuesday";
                    break;
                default: initial = " problem ";
            }
        }

        if (arrayList.get(position).getWed() == 1) {


            if (arrayList.get(position).getSun() != 1 && arrayList.get(position).getMon() != 1 && arrayList.get(position).getTue() != 1)
                check = "bl";
            else
                check = ",";

            if (arrayList.get(position).getThur() == 0 && arrayList.get(position).getFri() == 0
                    && arrayList.get(position).getSat() == 0)
                check = "or";

            switch (check) {
                case ",":
                    initial = initial + ", Wednesday";
                    break;
                case "bl":
                    initial = initial + " Wednesday";
                    break;
                case "or":
                    initial = initial + " or Wednesday";
                    break;
                default:
                    initial = " problem ";
            }
        }

        if (arrayList.get(position).getThur() == 1) {
            if (arrayList.get(position).getSun() != 1 && arrayList.get(position).getMon() != 1 && arrayList.get(position).getTue() != 1 && arrayList.get(position).getWed()!=1)
                check = "bl";
            else
                check = ",";

            if (arrayList.get(position).getFri() == 0
                    && arrayList.get(position).getSat() == 0)
                check = "or";

            switch (check) {
                case ",":
                    initial = initial + ", Thursday";
                    break;
                case "bl":
                    initial = initial + " Thursday";
                    break;
                case "or":
                    initial = initial + " or Thursday";
                    break;
                default:
                    initial = " problem ";
            }
        }

        if (arrayList.get(position).getFri() == 1) {
            if (arrayList.get(position).getSun() != 1 && arrayList.get(position).getMon() != 1
                    && arrayList.get(position).getTue() != 1 && arrayList.get(position).getWed()!=1 && arrayList.get(position).getThur()!=1)
                check = "bl";
            else
                check = ",";

            if (arrayList.get(position).getSat() == 0)
                check = "or";

            switch (check) {
                case ",":
                    initial = initial + ", Friday";
                    break;
                case "bl":
                    initial = initial + " Friday";
                    break;
                case "or":
                    initial = initial + " or Friday";
                    break;
                default:
                    initial = " problem ";
            }
        }

        if (arrayList.get(position).getSat() == 1) {
            if (arrayList.get(position).getSun() != 1 && arrayList.get(position).getMon() != 1
                    && arrayList.get(position).getTue() != 1 && arrayList.get(position).getWed()!=1
                    && arrayList.get(position).getThur()!=1 && arrayList.get(position).getFri()!=1)
                check = "bl";
            else
                check = "or";

            switch (check) {
                case ",":
                    initial = initial + ", Saturday";
                    break;
                case "bl":
                    initial = initial + " Saturday";
                    break;
                case "or":
                    initial = initial + " or Saturday";
                    break;
                default:
                    initial = " problem ";
            }
        }

        initial = initial + ".";

        if (initial.equals("When it's Sunday, Monday, Tuesday, Wednesday, Thursday, Friday or Saturday."))
            holder.repeatDaysTextView.setText("Everyday");
        else
            holder.repeatDaysTextView.setText(initial);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    static class ProfileViewHolder extends RecyclerView.ViewHolder {
        //  private TextView subName;
        private SwipeLayout swipeLayout;

        TextView profileTextView;
        TextView from_till_TextView;
        TextView edit;
        TextView delete;
        TextView repeatDaysTextView;
        ImageView enableImageView;
        ImageView start_profileTypeImageView;
        ImageView end_profileTypeImageView;
        CardView cardRoot;


        public ProfileViewHolder(View itemView) {
            super(itemView);

            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);

            profileTextView = (TextView) itemView
                    .findViewById(R.id.profile_text);
            from_till_TextView = (TextView) itemView
                    .findViewById(R.id.from_till_text);
            repeatDaysTextView = (TextView) itemView
                    .findViewById(R.id.repeat_days);

            enableImageView = (ImageView) itemView.findViewById(R.id.enable_imageview);
            start_profileTypeImageView = (ImageView) itemView.findViewById(R.id.start_profile_type_image_view);
            end_profileTypeImageView = (ImageView) itemView.findViewById(R.id.end_profile_type_image_view);


            edit = (TextView) itemView.findViewById(R.id.edit);
            delete = (TextView) itemView.findViewById(R.id.delete);

            cardRoot = (CardView) itemView.findViewById(R.id.card_root);
        }
    }
}



