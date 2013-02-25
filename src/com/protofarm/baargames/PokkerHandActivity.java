package com.protofarm.baargames;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.protofarm.poker.Card;
import com.protofarm.poker.Deck;
import com.protofarm.poker.Player;

/**
 * TODO Put here a description of what this class does.
 * 
 * @author kyle. Created Apr 4, 2011.
 */
public class PokkerHandActivity extends Activity {

	/**
	 * Global variable
	 */
	static Player player = new Player("Bob");
	static ImageButton card_1;
	static ImageButton card_2;
	static ImageButton card_3;
	static ImageButton card_4;
	static ImageButton card_5;
	static TextView ticker;


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
		setContentView(R.layout.game);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		card_1 = (ImageButton) findViewById(R.id.card_1);
		card_2 = (ImageButton) findViewById(R.id.card_2);
		card_3 = (ImageButton) findViewById(R.id.card_3);
		card_4 = (ImageButton) findViewById(R.id.card_4);
		card_5 = (ImageButton) findViewById(R.id.card_5);
		ticker = (TextView) findViewById(R.id.tickertape);

		// Setup
		card_1.setBackgroundResource(getDrawableByName("card_vr"));
		card_2.setBackgroundResource(getDrawableByName("card_vr"));
		card_3.setBackgroundResource(getDrawableByName("card_vr"));
		card_4.setBackgroundResource(getDrawableByName("card_vr"));
		card_5.setBackgroundResource(getDrawableByName("card_vr"));

		card_1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dealAHand();
			}
		});
		
		card_2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dealAHand();
			}
		});
		
		card_3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dealAHand();
			}
		});
		
		card_4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dealAHand();
			}
		});
		
		card_5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dealAHand();
			}
		});
		
	}

	void dealAHand() {
		String tickermsg;

		ArrayList<Card> empty = new ArrayList();
		player.setHand(empty);

		// new deck and clean hand

		Deck deck = new Deck();
		deck.shuffle();
		Log.d("KK", "card # " + deck.getRemainingCnt());

		for (int i = 1; i < 6; i++) {
			player.takeCard(deck.deal());
		}

		ArrayList<Card> hand = player.getHand();

		char[] handHex = PokkerUtil.getHandValue(hand);
		tickermsg = PokkerUtil.descWinningHand(handHex);
		String hexString = new String(handHex);
		ticker.setText(tickermsg);

		// Image Buttons
		card_1.setBackgroundResource(getDrawableByName("card_"
				+ getFace(hand.get(0))));
		card_2.setBackgroundResource(getDrawableByName("card_"
				+ getFace(hand.get(1))));
		card_3.setBackgroundResource(getDrawableByName("card_"
				+ getFace(hand.get(2))));
		card_4.setBackgroundResource(getDrawableByName("card_"
				+ getFace(hand.get(3))));
		card_5.setBackgroundResource(getDrawableByName("card_"
				+ getFace(hand.get(4))));
	}

}
