package com.vovka.egov66client.ui.login

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vovka.egov66client.R
import com.vovka.egov66client.databinding.FragmentLoginBinding
import com.vovka.egov66client.databinding.FragmentProfileBinding
import com.vovka.egov66client.ui.profile.ProfileViewModel
import com.vovka.egov66client.utils.collectWhenStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlin.text.split

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels()

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)
        initCallback()
        subscribe()
    }

    private fun initCallback() {

        //TODO Костыль
        requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).visibility = View.GONE
        requireActivity().actionBar?.hide()
        binding.webview.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
        }

        binding.webview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.d("LoginFragment", "Page finished loading: $url")
                checkForLocalStorage()
                //TODO При первой загрузке не перекидывает в меню, если перезайти то перекинет куда надо
            }

            override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
                super.onReceivedError(view, errorCode, description, failingUrl)
                Log.e("LoginFragment", "WebView error: $description")
            }
        }

        binding.webview.loadUrl("https://dnevnik.egov66.ru/")


        binding.webview.addJavascriptInterface(object {
            @android.webkit.JavascriptInterface
            fun onTokenFound(token: String) {
                viewModel.updateToken(token)
//                Log.d("LoginFragment", "Found Auth__token: $token")
//                activity?.runOnUiThread {
//                    Toast.makeText(requireContext(), "Auth token found", Toast.LENGTH_LONG).show()
//                }
            }

            @android.webkit.JavascriptInterface
            fun onTokenNotFound() {
                Log.d("LoginFragment", "Auth__token not found in localStorage")
                activity?.runOnUiThread {
//                    Toast.makeText(requireContext(), "Auth token not found", Toast.LENGTH_LONG).show()
                }
            }

            @android.webkit.JavascriptInterface
            fun onError(error: String) {
                Log.e("LoginFragment", "Error accessing localStorage: $error")
                activity?.runOnUiThread {
                    Toast.makeText(requireContext(), "Error: $error", Toast.LENGTH_LONG).show()
                }
            }
        }, "Android")
    }
    //TODO прятать webview, и смотреть есть ли токен уже. Если есть то вперед

    private fun checkForLocalStorage() {
        val js = """
            (function() {
                try {
                    var token = localStorage.getItem('Auth__token');
                    if (token) {
                        Android.onTokenFound(token);
                    } else {
                        Android.onTokenNotFound();
                    }
                } catch(e) {
                    Android.onError(e.toString());
                }
            })();
        """.trimIndent()

        binding.webview.evaluateJavascript(js, null)
    }

    private fun subscribe() {
        viewModel.action.collectWhenStarted(this) { action ->
            when(action) {
                LoginViewModel.Action.OpenSchedule -> {
                    requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).visibility = View.VISIBLE
                    requireActivity().actionBar?.show()
                    findNavController().navigate(R.id.navigation_home)

                }
            }
        }
    }


}