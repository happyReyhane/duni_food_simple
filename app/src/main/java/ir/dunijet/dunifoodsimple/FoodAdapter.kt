package ir.dunijet.dunifoodsimple

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ir.dunijet.dunifoodsimple.databinding.ItemFoodBinding
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class FoodAdapter(private val data: ArrayList<Food>, private val foodEvents: FoodEvents) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {



    inner class FoodViewHolder(private val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindData(position: Int) {

            binding.itemTxtSubject.text = data[position].txtSubject
            binding.itemTxtCity.text = data[position].txtCity
            binding.itemTxtPrice.text = "$" + data[position].txtPrice + " vip "
            binding.itemTxtDistanse.text = data[position].txtDistance + " miles from you "
            binding.itemRatingMain.rating = data[position].rating
            binding.itemTxtRating.text = data[position].numOfRating.toString() + " rating "

            Glide
                .with(binding.root.context)
                .load(data[position].urlImage)
                .transform(RoundedCornersTransformation(16, 4))
                .into(binding.itemImgMain)

            itemView.setOnClickListener {

                foodEvents.onFoodClicked(data[adapterPosition] , adapterPosition)

            }
            itemView.setOnLongClickListener {

                foodEvents.onFoodLongClicked(data[adapterPosition] , adapterPosition)

                true

            }

        }


    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
      val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return FoodViewHolder(binding)

        Log.v("testApp", "onCreateViewHolderCalled")
    }
    override fun getItemCount(): Int {
        return data.size
    }
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bindData(position)
        Log.v("testApp", "onBindViewHolderCalled")
    }

    fun addFood(newFood: Food) {
        //add food to list
        data.add(0, newFood)
        notifyItemInserted(0)
    }
    fun removeFood(oldFood: Food, oldPosition: Int) {
        //remove item from list
        data.remove(oldFood)
        notifyItemRemoved(oldPosition)

    }
    fun updateFood(newFood: Food , position: Int){

        data.set(position , newFood)
        notifyItemChanged(position)

    }
    fun setData(newList: ArrayList<Food>){
        //set new data to list
        data.clear()
        data.addAll(newList)
        notifyDataSetChanged()
    }

    interface FoodEvents {
        fun onFoodClicked(food: Food , position: Int)

        fun onFoodLongClicked(food: Food, pos: Int)
    }

}