package net.spinel.hexcards.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import net.spinel.hexcards.HEXApplication;
import net.spinel.hexcards.R;
import net.spinel.hexcards.models.Card;
import net.spinel.hexcards.utils.CostColorFilter;

public class CardDisplayActivity extends AppCompatActivity {
    private TextView tvCardName, tvCardNameEN, tvRarity, tvVersion, tvType, tvUnique, tvRule,
            tvRequirement, tvStatus, tvDescription;
    private ImageView ivPic, ivRarity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_display);

        initView();

        Card card = (Card) getIntent().getSerializableExtra("card");

        //card_name
        tvCardName.setText(card.getName());

        //card_name_en
        String cardNameEN = card.getName_en();
        tvCardNameEN.setText(cardNameEN.isEmpty() ? "-" : cardNameEN);

        //rarity
        String rarity;
        int rarity_shape;
        switch (card.getRarity()) {
            case "UC":
                rarity = this.getString(R.string.uncommon);
                rarity_shape = R.drawable.shape_uc;
                break;
            case "R":
                rarity = this.getString(R.string.rare);
                rarity_shape = R.drawable.shape_r;
                break;
            case "MR":
                rarity = this.getString(R.string.mythic_rare);
                rarity_shape = R.drawable.shape_mr;
                break;
            case "AA":
                rarity = this.getString(R.string.another_art);
                rarity_shape = R.drawable.shape_aa;
                break;
            case "TOKEN":
                rarity = this.getString(R.string.token);
                rarity_shape = R.drawable.shape_c;
                break;
            default:
                rarity = this.getString(R.string.common);
                rarity_shape = R.drawable.shape_c;
        }
        ivRarity.setImageResource(rarity_shape);
        tvRarity.setText(rarity);

        //version
        String version = card.getVersion().equals("alpha") ?
                this.getString(R.string.alpha) : this.getString(R.string.beta);
        int totalCount = card.getVersion().equals("alpha") ?
                HEXApplication.ALPHA_COUNT : HEXApplication.BETA_COUNT;
        tvVersion.setText(String.format(this.getString(R.string.version), version, card.getCard_no(), totalCount));

        //rule
        tvRule.setText(card.getRule());

        //type
        String type = card.getType();
        if (!card.getSubtype().isEmpty()) {
            type += "～" + card.getSubtype();
        }
        tvType.setText(type);

        //card_image
        ImageLoader.getInstance().displayImage("assets://" + card.getImg_url() + ".jpg", ivPic);

        //requirement
        tvRequirement.setText(CostColorFilter.parseFaceByText(this, card.getRequirement()));

        //unique
        if (card.is_unique()) {
            tvUnique.setText(this.getString(R.string.unique));
        }

        //description
        tvDescription.setText(card.getDescription());

        //pow & def
        String status;
        if (card.getType().contains(getString(R.string.troop))) {
            status = String.format(getString(R.string.pow_def), card.getPower(), card.getDefense());
        } else {
            status = "-";
        }
        tvStatus.setText(status);
    }

    private void initView() {
        tvCardName = (TextView) this.findViewById(R.id.tv_card_name);
        tvCardNameEN = (TextView) this.findViewById(R.id.tv_card_name_en);
        tvRarity = (TextView) this.findViewById(R.id.tv_rarity);
        tvVersion = (TextView) this.findViewById(R.id.tv_version);
        tvType = (TextView) this.findViewById(R.id.tv_type);
        tvUnique = (TextView) this.findViewById(R.id.tv_unique);
        tvRule = (TextView) this.findViewById(R.id.tv_rule);
        tvRequirement = (TextView) this.findViewById(R.id.tv_requirement);
        tvStatus = (TextView) this.findViewById(R.id.tv_status);
        tvDescription = (TextView) this.findViewById(R.id.tv_description);
        ivPic = (ImageView) this.findViewById(R.id.iv_pic);
        ivRarity = (ImageView) this.findViewById(R.id.iv_rarity);
    }
}
