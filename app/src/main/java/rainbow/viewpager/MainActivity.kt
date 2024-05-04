package rainbow.viewpager

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                Text("ViewPager:")
                VP1(
                    Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )
                Text("ViewPager2:")
                VP2(
                    Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun VP1(modifier: Modifier = Modifier) {
    AndroidView(
        factory = {
            ViewPager(it).apply {
                adapter = object : PagerAdapter() {
                    override fun instantiateItem(container: ViewGroup, position: Int) =
                        ComposeView(it).apply {
                            setContent { Text(position.toString()) }
                            container.addView(this)
                        }

                    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                        container.removeView(`object` as View)
                    }

                    override fun getCount() = 10
                    override fun isViewFromObject(view: View, `object`: Any) = view == `object`
                }
            }
        },
        modifier = modifier
    )
}

@Composable
fun VP2(modifier: Modifier = Modifier) {
    AndroidView(
        factory = {
            ViewPager2(it).apply {
                adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
                        object : RecyclerView.ViewHolder(
                            ComposeView(it).apply {
                                layoutParams = LayoutParams(
                                    LayoutParams.MATCH_PARENT,
                                    LayoutParams.MATCH_PARENT
                                )
                            }
                        ) {}

                    override fun getItemCount() = 10

                    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
                        (holder.itemView as ComposeView).setContent { Text(position.toString()) }
                }
            }
        },
        modifier = modifier
    )
}
