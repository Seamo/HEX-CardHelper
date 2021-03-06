package net.spinel.hexcards.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import net.spinel.hexcards.R;
import net.spinel.hexcards.models.Card;
import net.spinel.hexcards.utils.CostColorFilter;

import java.util.List;

/**
 * Created by Spinel on 16/5/17.
 */
public class CardListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Card> mList;

    public CardListAdapter(Context context) {
        this.mContext = context;
    }

    public void bindData(List<Card> list) {
        this.mList = list;
    }

    public void addData(List<Card> list) {
        this.mList.addAll(list);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Card getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder mHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_cardlist, null);
            mHolder = new ViewHolder();
            mHolder.tvCardName = (TextView) convertView.findViewById(R.id.tv_card_name);
            mHolder.tvRarity = (TextView) convertView.findViewById(R.id.tv_rarity);
            mHolder.tvVersion = (TextView) convertView.findViewById(R.id.tv_version);
            mHolder.tvType = (TextView) convertView.findViewById(R.id.tv_type);
            mHolder.tvRule = (TextView) convertView.findViewById(R.id.tv_rule);
            mHolder.tvRequirement = (TextView) convertView.findViewById(R.id.tv_requirement);
            mHolder.tvCost = (TextView) convertView.findViewById(R.id.tv_cost);
            mHolder.tvUnique = (TextView) convertView.findViewById(R.id.tv_unique);
            mHolder.ivPic = (ImageView) convertView.findViewById(R.id.iv_pic);
            mHolder.ivRarity = (ImageView) convertView.findViewById(R.id.iv_rarity);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        //card_name
        mHolder.tvCardName.setText(getItem(position).getName());

        //rarity
        String rarity;
        int rarity_shape;
        switch (getItem(position).getRarity()) {
            case "UC":
                rarity = mContext.getString(R.string.uncommon);
                rarity_shape = R.drawable.shape_uc;
                break;
            case "R":
                rarity = mContext.getString(R.string.rare);
                rarity_shape = R.drawable.shape_r;
                break;
            case "MR":
                rarity = mContext.getString(R.string.mythic_rare);
                rarity_shape = R.drawable.shape_mr;
                break;
            case "AA":
                rarity = mContext.getString(R.string.another_art);
                rarity_shape = R.drawable.shape_aa;
                break;
            case "TOKEN":
                rarity = mContext.getString(R.string.token);
                rarity_shape = R.drawable.shape_c;
                break;
            default:
                rarity = mContext.getString(R.string.common);
                rarity_shape = R.drawable.shape_c;
        }
        mHolder.ivRarity.setImageResource(rarity_shape);
        mHolder.tvRarity.setText(rarity);

        //set
        int set = getItem(position).getSet();
        String set_name;
        switch (set) {
            case 1:
                set_name = mContext.getString(R.string.set1);
                break;
            case 2:
                set_name = mContext.getString(R.string.set2);
                break;
            case 3:
                set_name = mContext.getString(R.string.set3);
                break;
            default:
                set_name = "";
                break;
        }
        mHolder.tvVersion.setText(set_name);

        //rule
        mHolder.tvRule.setText(getItem(position).getRule());

        //type
        String type = getItem(position).getType();
        if (!getItem(position).getSubtype().isEmpty()) {
            type += "～" + getItem(position).getSubtype();
        }
        mHolder.tvType.setText(type);

        //card_image
        ImageLoader.getInstance().displayImage("assets://" + getItem(position).getImg_url() + ".jpg", mHolder.ivPic);

        //requirement
        mHolder.tvRequirement.setText(CostColorFilter.parseFaceByText(mContext, getItem(position).getRequirement()));

        //cost
        String cost = String.format(mContext.getString(R.string.cost_format), getItem(position).getCost());
        mHolder.tvCost.setText(cost);

        //unique
        if (getItem(position).is_unique()) {
            mHolder.tvUnique.setText(mContext.getString(R.string.unique));
        }
        return convertView;
    }

    class ViewHolder {
        TextView tvCardName, tvRarity, tvVersion, tvType, tvRule, tvRequirement, tvCost, tvUnique;
        ImageView ivPic, ivRarity;
    }
}
