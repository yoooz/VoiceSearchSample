package com.example.voicesearchsample

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        micButton.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.chat_something))
            try {
                startActivityForResult(intent, REQUEST_CODE_RECOGNIZER)
            } catch (e : ActivityNotFoundException) {
                Toast.makeText(this, getString(R.string.cant_use_voice_control), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_RECOGNIZER && resultCode == Activity.RESULT_OK) {
            val query = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.firstOrNull() ?: return
            if (query.isNotEmpty()) {
                textView.text = query
            } else {
                Toast.makeText(this, getString(R.string.voice_control_error), Toast.LENGTH_SHORT).show()
            }

        }
    }

    companion object {
        private const val REQUEST_CODE_RECOGNIZER = 100
    }
}