package com.protofarm.baargames;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.protofarm.poker.Card;
import com.protofarm.poker.Deck;
import com.protofarm.poker.Player;

/**
 * TODO Put here a description of what this class does.
 *
 * @author kyle.
 *         Created Apr 27, 2011.
 */
public class IndianActivity extends Activity{
	
	// returns ID of drawable given by name (i.e. getDrawableByName("app_icon"
	// );
	public static int getDrawableByName(String name) {
		int drawableId = 0;

		try {
			Class res = R.drawable.class;
			Field field = res.getField(name);
			drawableId = field.getInt(null);
		} catch (Exception e) {
			// Toast.makeText(context,
			// "DIT Service: onStartCommand()",Toast.LENGTH_SHORT).show();
			Log.e("DateInTray", "Failure to get drawable: " + name, e);
		}

		return (drawableId);
	}

	private static String getFace(Card card) {
		String face = card.getRankFace(card.rank()).toLowerCase()
				+ card.suit().toString().toLowerCase();
		return face;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.indian);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		final Button dealButton = (Button) findViewById(R.id.btn_deal);

		final Player player = new Player("Bob");

		//final ImageView card = (ImageView) findViewById(R.id.imageViewIndian);

		// Setup
		dealButton.setBackgroundResource(getDrawableByName("card_vr"));


		dealButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				String refName;


				// new deck and clean hand

				Deck deck = new Deck();
				deck.shuffle();

				for (int i = 1; i < 6; i++) {
					player.takeCard(deck.deal());
				}
				
				Card dealtCard = deck.deal();

				// Image Buttons

				refName = "card_" + getFace(dealtCard);
				if (v == dealButton) {
					(dealButton).setBackgroundResource(getDrawableByName(refName));
				}


			}

		});

	}


}
