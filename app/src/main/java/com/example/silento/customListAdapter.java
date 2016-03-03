package com.example.silento;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 1305166 on 02-10-2015.
 */
public class customListAdapter extends ArrayAdapter<profileParcel> {
    private Context context;
    List<profileParcel> profileParcel1;
    private DisplayMetrics metrics_;

    public customListAdapter(Context context, List<profileParcel> profileParcel1 , DisplayMetrics metrics) {
        super(context, R.layout.custom_single_row, profileParcel1);

        this.context = context;
        this.profileParcel1 = profileParcel1;
        this.metrics_ = metrics;
    }

    private class ViewHolder {
        TextView profileTextView;
        TextView from_till_TextView;
        TextView repeatDaysTextView;
        ImageView enableImageView;
        ImageView start_profileTypeImageView;
        ImageView end_profileTypeImageView;
        View layout;
    }

    @Override
    public int getCount() {
        return profileParcel1.size();
    }

    @Override
    public profileParcel getItem(int position) {
        return profileParcel1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        Animation animation = null;
        animation = AnimationUtils.loadAnimation(context, R.anim.push_up_in);
        //animation = AnimationUtils.loadAnimation(context, R.anim.push_up_in);
        //animation = AnimationUtils.loadAnimation(context, R.anim.push_left_in);
        //animation = new TranslateAnimation(0, 0, metrics_.heightPixels, 0);



        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_single_row, null);
            holder = new ViewHolder();

            holder.profileTextView = (TextView) convertView
                    .findViewById(R.id.profile_text);
            holder.from_till_TextView = (TextView) convertView
                    .findViewById(R.id.from_till_text);
            holder.repeatDaysTextView = (TextView) convertView
                    .findViewById(R.id.repeat_days);
            holder.enableImageView = (ImageView) convertView.findViewById(R.id.enable_imageview);
            holder.start_profileTypeImageView = (ImageView) convertView.findViewById(R.id.start_profile_type_image_view);
            holder.end_profileTypeImageView = (ImageView) convertView.findViewById(R.id.end_profile_type_image_view);
            //holder.profile_state = (RadioButton) convertView
            //      .findViewById(R.id.profile_state);

            // holder.layout = convertView.findViewById(R.id.custom_row);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        profileParcel profileParcel2 = (profileParcel) getItem(position);

        holder.profileTextView.setText(profileParcel2.getProfileName() + "");

        holder.from_till_TextView.setText(profileParcel2.getStartHour() + ":"
                + profileParcel2.getStartMinute() + " - "
                + profileParcel2.getEndHour() + ":"
                + profileParcel2.getEndMinute() + "");

        if (profileParcel2.getStatus() == 0)
            holder.enableImageView.setImageResource(R.mipmap.ic_action_toggle_radio_button_off);
        else
            holder.enableImageView.setImageResource(R.mipmap.ic_action_radio_button_on);

        if (profileParcel2.getstart_profileType().equals("Silent"))
            holder.start_profileTypeImageView.setImageResource(R.mipmap.ic_action_silent);
        else if (profileParcel2.getstart_profileType().equals("Vibration"))
            holder.start_profileTypeImageView.setImageResource(R.mipmap.ic_action_shake_me);
        else
            holder.start_profileTypeImageView.setImageResource(R.mipmap.ic_action_normal);


        if (profileParcel2.getend_profileType().equals("Silent"))
            holder.end_profileTypeImageView.setImageResource(R.mipmap.ic_action_silent);
        else if (profileParcel2.getend_profileType().equals("Vibration"))
            holder.end_profileTypeImageView.setImageResource(R.mipmap.ic_action_shake_me);
        else
            holder.end_profileTypeImageView.setImageResource(R.mipmap.ic_action_normal);


        getRepaetDays(holder, profileParcel2);

/*
        holder.layout.setBackgroundColor(subjectParcel2.getColor());

*/
        animation.setDuration(450);
        convertView.startAnimation(animation);
        animation = null;

        return convertView;
    }

    private void getRepaetDays(ViewHolder holder, profileParcel profileParcel2) {
        String check;
        check = "prob";
        CharSequence initial = "When it's";

        if (profileParcel2.getSun() == 1)
            initial = initial + " Sunday";

        if (profileParcel2.getMon() == 1) {


            if (profileParcel2.getSun() == 1)
                 check = ",";
            else if(profileParcel2.getSun() == 0)
                check ="bl";

            if (profileParcel2.getTue() == 0 && profileParcel2.getWed() == 0
                    && profileParcel2.getThur() == 0 && profileParcel2.getFri() == 0
                    && profileParcel2.getSat() == 0)
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

        if (profileParcel2.getTue() == 1) {



            if (profileParcel2.getSun() != 1 && profileParcel2.getMon()!=1)
                check = "bl";
            else
                check =",";

            if (profileParcel2.getWed() == 0
                    && profileParcel2.getThur() == 0 && profileParcel2.getFri() == 0
                    && profileParcel2.getSat() == 0)
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

        if (profileParcel2.getWed() == 1) {


            if (profileParcel2.getSun() != 1 && profileParcel2.getMon() != 1 && profileParcel2.getTue() != 1)
                check = "bl";
            else
                check = ",";

            if (profileParcel2.getThur() == 0 && profileParcel2.getFri() == 0
                    && profileParcel2.getSat() == 0)
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

        if (profileParcel2.getThur() == 1) {
            if (profileParcel2.getSun() != 1 && profileParcel2.getMon() != 1 && profileParcel2.getTue() != 1 && profileParcel2.getWed()!=1)
                check = "bl";
            else
                check = ",";

            if (profileParcel2.getFri() == 0
                    && profileParcel2.getSat() == 0)
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

        if (profileParcel2.getFri() == 1) {
            if (profileParcel2.getSun() != 1 && profileParcel2.getMon() != 1
                    && profileParcel2.getTue() != 1 && profileParcel2.getWed()!=1 && profileParcel2.getThur()!=1)
                check = "bl";
            else
                check = ",";

            if (profileParcel2.getSat() == 0)
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

        if (profileParcel2.getSat() == 1) {
            if (profileParcel2.getSun() != 1 && profileParcel2.getMon() != 1
                    && profileParcel2.getTue() != 1 && profileParcel2.getWed()!=1
                    && profileParcel2.getThur()!=1 && profileParcel2.getFri()!=1)
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
    public void add(profileParcel profileParcel2) {
        profileParcel1.add(profileParcel2);
        notifyDataSetChanged();
        super.add(profileParcel2);
    }

    @Override
    public void remove(profileParcel profileParcel2) {
        profileParcel1.remove(profileParcel2);
        notifyDataSetChanged();
        super.remove(profileParcel2);
    }


}
