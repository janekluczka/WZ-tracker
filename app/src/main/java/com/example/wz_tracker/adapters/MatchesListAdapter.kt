package com.example.wz_tracker.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.wz_tracker.R
import com.example.wz_tracker.database.Match
import com.example.wz_tracker.util.BackgroundUtil
import com.example.wz_tracker.util.DateFormatterUtil
import com.example.wz_tracker.util.ModeNameUtil
import com.example.wz_tracker.util.PlaceTextUtil

class MatchesListAdapter(
    private val context: Context,
    private val matches: Array<Match>,
    private val onClickListener: (Int) -> Unit
) : RecyclerView.Adapter<MatchesListAdapter.GameViewHolder>() {

    class GameViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val gamePlaceTextView: TextView
        val gameModeTextView: TextView
        val gameDateTextView: TextView
        val gameBackground: LinearLayout
        val card: CardView

        init {
            gamePlaceTextView = view.findViewById(R.id.card_game_tv_place)
            gameModeTextView = view.findViewById(R.id.card_game_tv_mode)
            gameDateTextView = view.findViewById(R.id.card_game_tv_date)
            gameBackground = view.findViewById(R.id.card_game_ll_left)
            card = view.findViewById(R.id.card_game_card)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater
            .from(viewGroup.context)
            .inflate(R.layout.card_game, viewGroup, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: GameViewHolder, position: Int) =
        setUpViewHolder(viewHolder, matches[position])

    private fun setUpViewHolder(viewHolder: GameViewHolder, match: Match) {
        setTextViews(viewHolder, match)
        setBackgroundBasedOnPlace(viewHolder, match)
        setCardOnClickListener(viewHolder, match)
    }

    private fun setTextViews(viewHolder: GameViewHolder, match: Match) {
        viewHolder.gamePlaceTextView.text = getPlaceOrdinalNumber(match)
        viewHolder.gameModeTextView.text = getMatchMode(match)
        viewHolder.gameDateTextView.text = getTimePassed(match)
    }

    private fun getPlaceOrdinalNumber(match: Match) =
        PlaceTextUtil.getPlaceOrdinalNumber(match.place)

    private fun getMatchMode(match: Match) = ModeNameUtil.getMatchMode(match.mode)

    private fun getTimePassed(match: Match) = DateFormatterUtil.getTimePassed(match.date)

    private fun setBackgroundBasedOnPlace(viewHolder: GameViewHolder, match: Match) {
        val drawable = BackgroundUtil.getCardBackgroundBasedOnPlace(match.place)
        viewHolder.gameBackground.background = AppCompatResources.getDrawable(context, drawable)
    }

    private fun setCardOnClickListener(viewHolder: GameViewHolder, match: Match) =
        viewHolder.card.setOnClickListener {
            onClickListener(match.id)
        }

    override fun getItemCount() = matches.size

}