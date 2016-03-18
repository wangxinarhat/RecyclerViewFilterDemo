package wang.wangxinarhat.recyclerviewfilterdemo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wang on 2016/3/18.
 */
public class SearchPeopleHolder extends RecyclerView.ViewHolder {


    @Bind(R.id.holder_search_people_avatar)
    ImageView avatar;

    @Bind(R.id.holder_search_people_name)
    TextView name;

    @Bind(R.id.holder_search_people_des)
    TextView des;

    public SearchPeopleHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(People people) {

        ArrayList<Integer> avatarList = new ArrayList<>();


        avatarList.add(R.mipmap.avatar1);
        avatarList.add(R.mipmap.avatar2);
        avatarList.add(R.mipmap.avatar3);
        avatarList.add(R.mipmap.avatar4);
        avatarList.add(R.mipmap.avatar5);
        avatarList.add(R.mipmap.avatar6);
        avatarList.add(R.mipmap.avatar7);
        avatarList.add(R.mipmap.avatar8);

        name.setText(people.getName());
        des.setText(people.getDescription());
        avatar.setImageResource(avatarList.get(new Random().nextInt(avatarList.size())));
    }


}
