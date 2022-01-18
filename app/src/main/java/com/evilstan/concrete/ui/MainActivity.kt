package com.evilstan.concrete.ui

import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.evilstan.concrete.*
import com.evilstan.concrete.core.InputFilterMinMax
import com.evilstan.concrete.core.Tutorial
import com.evilstan.concrete.core.VolumeCalculator
import com.evilstan.concrete.data.Concrete
import com.evilstan.concrete.data.Construction
import com.evilstan.concrete.data.ManualVolume
import com.evilstan.concrete.databinding.ActivityMainBinding
import com.evilstan.concrete.ui.core.AddVolumeDialogListener
import com.evilstan.concrete.ui.core.ClearDialogListener
import com.evilstan.concrete.ui.core.ConstructionsAdapter
import com.evilstan.concrete.core.SwipeToDeleteCallback


class MainActivity : AppCompatActivity(),
    View.OnClickListener,
    View.OnFocusChangeListener,
    TextView.OnEditorActionListener,
    ClearDialogListener,
    AddVolumeDialogListener,
    ConstructionsAdapter.OnItemDeleteListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dataset: MutableList<Concrete>
    private lateinit var constructionsAdapter: ConstructionsAdapter
    private var onStart = true
    private var calculator = VolumeCalculator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setTitle(R.string.concrete_calculation)
        initComponents()
    }

    private fun initComponents() {
        initRecyclerView()
        initEditText()
        initButtons()
    }

    private fun initRecyclerView() {
        dataset = Tutorial(this).makeTutorial()

        constructionsAdapter = ConstructionsAdapter(dataset, this)
        binding.recyclerConstruction.layoutManager = LinearLayoutManager(this)
        binding.recyclerConstruction.adapter = constructionsAdapter

        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(constructionsAdapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerConstruction)
    }

    private fun initEditText() {
        val filterDimensions = Array<InputFilter>(1) { InputFilterMinMax(0.0, 100000.0) }
        val filterQuantity = Array<InputFilter>(1) { InputFilterMinMax(0.0, 100.0) }

        binding.editHeight.filters = filterDimensions
        binding.editWidth.filters = filterDimensions
        binding.editLength.filters = filterDimensions
        binding.editQuantity.filters = filterQuantity

        binding.editLength.onFocusChangeListener = this
        binding.editWidth.onFocusChangeListener = this
        binding.editHeight.onFocusChangeListener = this
        binding.editQuantity.onFocusChangeListener = this
        binding.editQuantity.setOnEditorActionListener(this)

        binding.editLength.requestFocus()
    }

    private fun initButtons() {
        binding.fBtnAdd.setOnClickListener(this)
        binding.fBtnManual.setOnClickListener(this)
        binding.fBtnHelp.setOnClickListener(this)
        binding.fBtnClear.setOnClickListener(this)
    }

    override fun onFocusChange(view: View, isFocused: Boolean) {
        val editText: EditText = view as EditText
        if (isFocused) {
            if (editText.id == binding.editQuantity.id) editText.setText("1")
            editText.selectAll()
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            binding.fBtnAdd.id -> addConstruction()
            binding.fBtnManual.id -> AddVolumeDialog(this, this).makeDialog()
            binding.fBtnHelp.id -> onClear()
            binding.fBtnClear.id -> ClearListDialog(this, this).showDialog()

        }
    }

    override fun onEditorAction(view: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_DONE)
            addConstruction()
        return true
    }

    private fun addConstruction() {
        try {
            val length = binding.editLength.text.toString().toDouble()
            val width = binding.editWidth.text.toString().toDouble()
            val height = binding.editHeight.text.toString().toDouble()
            val quantity = binding.editQuantity.text.toString().toInt()

            addItemToDataset(Construction(length, width, height, quantity))
        } catch (e: NumberFormatException) {
            Toast.makeText(this, resources.getString(R.string.wrong_data), Toast.LENGTH_SHORT)
                .show()
            setFocusOnEmptyField()
        }
    }

    private fun addItemToDataset(concrete: Concrete) {
        checkStart()
        dataset.add(concrete)
        constructionsAdapter.notifyItemInserted(dataset.size - 1)
        setTotalVolume()
        binding.editLength.requestFocus()
        showKeyboard()
    }

    private fun checkStart() {
        if (onStart) {
            val itemCount = dataset.size
            dataset.clear()
            constructionsAdapter.notifyItemRangeRemoved(0, itemCount)
            onStart = false
        }
    }

    private fun setTotalVolume() {
        val volume = calculator.calculate(dataset)
        val total =
            resources.getString(R.string.total) + volume + resources.getString(R.string.cubic_meters)
        binding.textResult.text = total
    }

    private fun setFocusOnEmptyField() {
        if (binding.editQuantity.text.isEmpty())
            binding.editQuantity.requestFocus()

        if (binding.editHeight.text.isEmpty())
            binding.editHeight.requestFocus()

        if (binding.editWidth.text.isEmpty())
            binding.editWidth.requestFocus()

        if (binding.editLength.text.isEmpty())
            binding.editLength.requestFocus()
    }

    private fun showKeyboard() {
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.editLength, InputMethod.SHOW_FORCED)
    }

    override fun onClear() {
        val range = dataset.size
        dataset.clear()
        constructionsAdapter.notifyItemRangeRemoved(0, range)
        setTotalVolume()
        showKeyboard()
    }

    override fun onAdd(volume: Double, description: String) {
        addItemToDataset(ManualVolume(volume, description))
    }

    override fun onDelete() {
        setTotalVolume()
    }
}