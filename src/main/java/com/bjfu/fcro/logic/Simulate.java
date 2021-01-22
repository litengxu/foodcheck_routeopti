package com.bjfu.fcro.logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Random random = new Random(System.currentTimeMillis());

		int count = 10000;

		List<Double> pointX = new ArrayList<Double>(0);

		List<Double> pointY = new ArrayList<Double>(0);

		while (pointX.size() < count) {
			double x = random.nextDouble();
			while (pointX.contains(x))
				x = random.nextDouble();
			pointX.add(x);
		}
		while (pointY.size() < count) {
			double y = random.nextDouble();
			while (pointX.contains(y))
				y = random.nextDouble();
			pointY.add(y);
		}

		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < count; i++) {
			stringBuilder.append(pointX.get(i) * 100).append(" ").append(pointY.get(i) * 100);
			if (i < count - 1)
				stringBuilder.append("\n");
		}

		try {
			String location = URLDecoder
					.decode(Combine.class.getProtectionDomain().getCodeSource().getLocation().getFile(), "UTF-8");
			File file = new File(
					new File(location).getParentFile().getAbsolutePath() + File.separator + count +"模拟数据" + ".txt");
			if (!file.exists())
				file.createNewFile();
			FileOutputStream out = new FileOutputStream(file, true);
			out.write(stringBuilder.toString().getBytes("utf-8"));
			out.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
