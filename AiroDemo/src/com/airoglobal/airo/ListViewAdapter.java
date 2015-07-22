package com.airoglobal.airo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.airoglobal.airodemo.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;

public class ListViewAdapter extends BaseAdapter {

	// Declare Variables
	Context mContext;
	LayoutInflater inflater;
	private List<Numbers> numberlist = null;
	private ArrayList<Numbers> arraylist;
	protected int count;
	float width = 50;
	Bitmap bmTemp = null;
	ArrayList<Integer> listCount;
	Target tagret;

	public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
			boolean filter) {
		float ratio = Math.min((float) maxImageSize / realImage.getWidth(),
				(float) maxImageSize / realImage.getHeight());
		int width = Math.round((float) ratio * realImage.getWidth());
		int height = Math.round((float) ratio * realImage.getHeight());

		Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width, height,
				filter);
		return newBitmap;
	}

	public ListViewAdapter(Context context, List<Numbers> numberlist) {
		mContext = context;
		this.numberlist = numberlist;
		// this.listCount = count;
		inflater = LayoutInflater.from(mContext);
		this.arraylist = new ArrayList<Numbers>();
		this.arraylist.addAll(numberlist);

	}

	public class ViewHolder {
		TextView num;
		ImageView img;

	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;

		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.listview_item, null);
			// Locate the TextView in listview_item.xml
			holder.num = (TextView) view.findViewById(R.id.num);
			holder.img = (ImageView) view.findViewById(R.id.imgURl);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Set the results into TextView
		// Set the results into TextView
		holder.num.setText(numberlist.get(position).getNum());
		tagret = new Target() {

			@Override
			public void onPrepareLoad(Drawable arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBitmapLoaded(Bitmap arg0, LoadedFrom arg1) {
				bmTemp = scaleDown(arg0, width, true);

			}

			@Override
			public void onBitmapFailed(Drawable arg0) {
				// TODO Auto-generated method stub

			}

		};
		Picasso.with(inflater.getContext())
				.load("http://lorempixel.com/200/200/sports/" + (position + 1))
				.into(tagret);
		holder.img.setImageBitmap(bmTemp);
	

		return view;
	}

	@Override
	public int getCount() {
		return numberlist.size();
	}

	@Override
	public Numbers getItem(int position) {
		return numberlist.get(position);
	}

	// @Override
	// public void onDestroy() { // could be in onPause or onStop
	// Picasso.with(mContext).cancelRequest(target);
	// super.onDestroy();
	// }

	public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
		int targetWidth = 50;
		int targetHeight = 50;
		Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, targetHeight,
				Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(targetBitmap);
		Path path = new Path();
		path.addCircle(((float) targetWidth - 1) / 2,
				((float) targetHeight - 1) / 2,
				(Math.min(((float) targetWidth), ((float) targetHeight)) / 2),
				Path.Direction.CCW);

		canvas.clipPath(path);
		Bitmap sourceBitmap = scaleBitmapImage;
		canvas.drawBitmap(sourceBitmap, new Rect(0, 0, sourceBitmap.getWidth(),
				sourceBitmap.getHeight()), new Rect(0, 0, targetWidth,
				targetHeight), null);
		return targetBitmap;
	}
}