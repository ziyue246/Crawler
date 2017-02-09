package common.system;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class VerifyCodeRecognition {

	public static String trainPath;
	private static int isBlack(int colorInt) {
		Color color = new Color(colorInt);
		if (color.getRed() + color.getGreen() + color.getBlue() <= 100) {
			return 1;
		}
		return 0;
	}

	private static int isWhite(int colorInt) {
		Color color = new Color(colorInt);
		if (color.getRed() + color.getGreen() + color.getBlue() > 550) {
			return 1;
		}
		return 0;
	}

	private static BufferedImage removeBackgroud(String picFile) throws Exception {
		BufferedImage img = ImageIO.read(new File(picFile));
		int width = img.getWidth();
		int height = img.getHeight();
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				if (isWhite(img.getRGB(x, y)) == 1) {
					img.setRGB(x, y, Color.WHITE.getRGB());
				} else {
					img.setRGB(x, y, Color.BLACK.getRGB());
				}
			}
		}
		return img;
	}

	private static BufferedImage removeBlank(BufferedImage img) throws Exception {
		int width = img.getWidth();
		int height = img.getHeight();
		int start = 0;
		int end = 0;
		Label1: for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				if (isBlack(img.getRGB(x, y)) == 1) {
					start = y;
					break Label1;
				}
			}
		}
		Label2: for (int y = height - 1; y >= 0; --y) {
			for (int x = 0; x < width; ++x) {
				if (isBlack(img.getRGB(x, y)) == 1) {
					end = y;
					break Label2;
				}
			}
		}
		return img.getSubimage(0, start, width, end - start + 1);
	}

	private static List<BufferedImage> splitImage(BufferedImage img) throws Exception {

		List<BufferedImage> subImgs = new ArrayList<BufferedImage>();
		int width = img.getWidth();
		int height = img.getHeight();
		List<Integer> weightlist = new ArrayList<Integer>();
		for (int x = 0; x < width; ++x) {
			int count = 0;
			for (int y = 0; y < height; ++y) {
				if (isBlack(img.getRGB(x, y)) == 1) {
					count++;
				}
			}
			weightlist.add(count);
		}
		for (int i = 0; i < weightlist.size(); i++) {
			int length = 0;
			while (i < weightlist.size() && weightlist.get(i) > 0) {
				i++;
				length++;
			}
			if (length > 40) {
				subImgs.add(removeBlank(img.getSubimage(i - length, 0, length / 2, height)));
				subImgs.add(removeBlank(img.getSubimage(i - length / 2, 0, length / 2, height)));
			} else if (length > 10) {
				subImgs.add(removeBlank(img.getSubimage(i - length, 0, length, height)));
			}
		}

		return subImgs;

	}

	private static Map<BufferedImage, String> loadTrainData() throws Exception {
		Map<BufferedImage, String> map = new HashMap<BufferedImage, String>();

		String path = trainPath;//
		File file = new File(path);
		File[] tempList = file.listFiles();

		for (int i = 0; i < tempList.length; i++) {
			String filename = tempList[i].getName();
			if(filename.equals("tmp.jpg"))continue;
			String filepath = tempList[i].getPath();
			BufferedImage img = removeBackgroud(filepath);
			List<BufferedImage> listImg = splitImage(img);
			for (int j = 0; j < listImg.size(); j++) {
				if (filename.charAt(j) == '.')
					break;
				map.put(listImg.get(j), filename.charAt(j) + "");

			}
			// map.put(listImg.get(1),filename.charAt(1) + "");
			// map.put(listImg.get(2),filename.charAt(2) + "");
			// map.put(listImg.get(3),filename.charAt(3) + "");
		}
		return map;
	}

	private static String getSingleCharOcr(BufferedImage img, Map<BufferedImage, String> map) {
		String result = "";
		int width = img.getWidth();
		int height = img.getHeight();
		int max = 0;
		for (BufferedImage bi : map.keySet()) {
			int count = 0;
			Label1: for (int x = 0; x < width && x < bi.getWidth(); ++x) {
				for (int y = 0; y < height && y < bi.getHeight(); ++y) {
					// if (isWhite(img.getRGB(x, y)) == isWhite(bi.getRGB(x,
					// y))) {
					// count++;
					// }
					if (isWhite(img.getRGB(x, y)) == isWhite(bi.getRGB(x, y))) {
						count++;
					}
					if (isWhite(img.getRGB(x, y)) != isWhite(bi.getRGB(x, y))) {
						count--;
					}
				}
			}
			if (count > max) {
				max = count;
				result = map.get(bi);
			}
		}
		return result;
	}

	public static String getAllOcr(String file)  {
		
		if(trainPath==null){
			System.out.println("trainPathΪnull,������trainPath");
			return null;
		}
		try{
			BufferedImage img = removeBackgroud(file);
			List<BufferedImage> listImg = splitImage(img);
			Map<BufferedImage, String> map = loadTrainData();
			String result = "";
			for (BufferedImage bi : listImg) {
				result += getSingleCharOcr(bi, map);
			}
			return result;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws Exception {

		String path = "patentTest";
		
		trainPath = "patentTrain";
		File file = new File(path);
		File[] tempList = file.listFiles();

		for (File file2 : tempList) {
			System.out.println(file2.getPath());
			String result = getAllOcr(file2.getPath());
			System.out.println(result);
		}
	}
}
