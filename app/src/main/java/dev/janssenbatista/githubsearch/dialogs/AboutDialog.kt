package dev.janssenbatista.githubsearch.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment
import dev.janssenbatista.githubsearch.databinding.AboutBinding

class AboutDialog : DialogFragment() {

    private val binding by lazy { AboutBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.apply {
            tvDeveloperNameLink.setOnClickListener {
                openDeveloperGithub()
            }
            tvIconLink.setOnClickListener {
                openIcons8Link()
            }
        }
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).setView(binding.root).create()
    }

    private fun openDeveloperGithub() {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = DEVELOPER_GITHUB_LINK.toUri()
        }
        startActivity(intent)
    }

    private fun openIcons8Link() {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = ICONS_8_LINK.toUri()
        }
        startActivity(intent)
    }

    companion object {
        private const val DEVELOPER_GITHUB_LINK = "https://github.com/janssenbatista"
        private const val ICONS_8_LINK = "https://icons8.com"
        const val TAG = "AboutDialog"
    }
}