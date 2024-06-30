package ir.dunijet.dunifoodsimple

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.dunijet.dunifoodsimple.databinding.ActivityMainBinding
import ir.dunijet.dunifoodsimple.databinding.DialogAddNewItemBinding
import ir.dunijet.dunifoodsimple.databinding.DialogDeleteItemBinding
import ir.dunijet.dunifoodsimple.databinding.DialogUpdateItemBinding
import java.text.FieldPosition

class MainActivity : AppCompatActivity(), FoodAdapter.FoodEvents {
    lateinit var binding: ActivityMainBinding
    lateinit var myAdapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val foodList = arrayListOf(
            Food(
                "Piza",
                "15",
                "3",
                "Isfahan",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg",
                20,
                4.5F
            ),

            Food(
                "fish",
                "12",
                "8",
                "Tehran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg",
                13,
                3.5F
            ),

            Food(
                "lazania",
                "11",
                "9",
                "Mashhad",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg",
                24,
                2.5F
            ),

            Food(
                "rice",
                "16",
                "6",
                "yazd",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food4.jpg",
                20,
                4.5F
            ),

            Food(
                "kebab",
                "19",
                "2",
                "tabriz",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg",
                24,
                3.5F
            ),

            Food(
                "koko",
                "45",
                "14",
                "berlin",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg",
                21,
                1.5F
            ),

            Food(
                "sushi",
                "17",
                "24",
                "tabriz",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg",
                29,
                3.5F
            ),

            Food(
                "abgosht",
                "44",
                "25",
                "New Yourk",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg",
                20,
                4.5F
            ),

            Food(
                "ashack",
                "22",
                "4",
                "sabzevar",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg",
                20,
                5F
            ),

            Food(
                "age",
                "15",
                "5",
                "karag",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg",
                20,
                4.5F
            ),

            Food(
                "ghrmeh",
                "15",
                "3",
                "paric",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg",
                34,
                5F
            ),

            Food(
                "kitchen",
                "18",
                "3",
                "london",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg",
                20,
                2.5F
            )
        )

        myAdapter = FoodAdapter(foodList.clone() as ArrayList<Food>, this)

        binding.recyclerMain.adapter = myAdapter

        binding.recyclerMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)


        binding.btnAddNewFood.setOnClickListener {

            val dialog = AlertDialog.Builder(this).create()
            val dialogBinding = DialogAddNewItemBinding.inflate(layoutInflater)
            dialog.setView(dialogBinding.root)
            dialog.setCancelable(true)
            dialog.show()


            dialogBinding.dialogBtnDone.setOnClickListener {

                if (
                    dialogBinding.dialogEdtNameFood.length() > 0 &&
                    dialogBinding.dialogEdtFoodCity.length() > 0 &&
                    dialogBinding.dialogEdtFoodDistance.length() > 0 &&
                    dialogBinding.dialogEdtFoodPrice.length() > 0

                ) {

                    val txtName = dialogBinding.dialogEdtNameFood.text.toString()
                    val txtPrice = dialogBinding.dialogEdtFoodPrice.text.toString()
                    val txtDistance = dialogBinding.dialogEdtFoodDistance.text.toString()
                    val txtCity = dialogBinding.dialogEdtFoodCity.text.toString()
                    val txtRatingNumber: Int = (1..150).random()
                    val ratingBarStar: Float = (1..5).random().toFloat()
                    val randomForUrl = (0..12).random()
                    val urlPic = foodList[randomForUrl].urlImage

                    val newFood = Food(
                        txtName,
                        txtPrice,
                        txtDistance,
                        txtCity,
                        urlPic,
                        txtRatingNumber,
                        ratingBarStar
                    )
                    myAdapter.addFood(newFood)

                    dialog.dismiss()

                    binding.recyclerMain.scrollToPosition(0)

                } else {
                    Toast.makeText(this, "لطفا همه مفادیر وارذ کنید", Toast.LENGTH_SHORT).show()
                }

            }


        }

       binding.edtSearch.addTextChangedListener {edtTextInput->
           if (edtTextInput!!.isNotEmpty()){
             //filter data 'h'
               val cloneList = foodList.clone() as ArrayList<Food>

               val filteredList = cloneList.filter { foodItem->

                   foodItem.txtSubject.contains(edtTextInput)

               }

               myAdapter.setData(ArrayList(filteredList))

           }

           else{
               //show all data:
               myAdapter.setData(foodList.clone() as ArrayList<Food>)
           }
       }
    }

    override fun onFoodClicked(food: Food, position: Int) {
        val dialog = AlertDialog.Builder(this).create()
        val updateDialogBinding = DialogUpdateItemBinding.inflate(layoutInflater)
        dialog.setView(updateDialogBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        updateDialogBinding.dialogEdtNameFood.setText(food.txtSubject)
        updateDialogBinding.dialogEdtFoodCity.setText(food.txtCity)
        updateDialogBinding.dialogEdtFoodPrice.setText(food.txtPrice)
        updateDialogBinding.dialogEdtFoodDistance.setText(food.txtDistance)

        updateDialogBinding.dialogUpdateBtnCancel.setOnClickListener {
            dialog.dismiss()
        }
        updateDialogBinding.dialogUpdateBtnDone.setOnClickListener {

            if (updateDialogBinding.dialogEdtNameFood.length() > 0 &&
                updateDialogBinding.dialogEdtFoodCity.length() > 0 &&
                updateDialogBinding.dialogEdtFoodDistance.length() > 0 &&
                updateDialogBinding.dialogEdtFoodPrice.length() > 0
            ) {
                val txtName = updateDialogBinding.dialogEdtNameFood.text.toString()
                val txtPrice = updateDialogBinding.dialogEdtFoodPrice.text.toString()
                val txtDistance = updateDialogBinding.dialogEdtFoodDistance.text.toString()
                val txtCity = updateDialogBinding.dialogEdtFoodCity.text.toString()

                val newFood = Food(txtName , txtPrice , txtDistance , txtCity , food.urlImage , food.numOfRating , food.rating)


                myAdapter.updateFood(newFood , position)
                dialog.dismiss()

            } else {
                Toast.makeText(this, "لطفا همه مقادیر وارد کنید", Toast.LENGTH_SHORT).show()
            }



        }
    }


    override fun onFoodLongClicked(food: Food, pos: Int) {
        val dialog = AlertDialog.Builder(this).create()
        val dialogDeleteBinding = DialogDeleteItemBinding.inflate(layoutInflater)
        dialog.setView(dialogDeleteBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        dialogDeleteBinding.dialogBtnDeleteCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogDeleteBinding.dialogBtnDeleteSure.setOnClickListener {
            dialog.dismiss()
            myAdapter.removeFood(food, pos)

            Log.v("testLog" , "remove")

        }
    }



}