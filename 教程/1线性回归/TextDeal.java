package ploy;

import java.io.*;
import java.util.List;

/**
 * @author wuweifeng wrote on 2018/6/5.
 */
public class TextDeal {
    public static void main(String[] args) throws IOException {
        new TextDeal().linePower("/Users/wuwf/Downloads/ml_data/0线性回归/autoMpg.csv",
                "/Users/wuwf/Downloads/ml_data/0线性回归/autoMpg_power.csv", 3, null);
    }

    /**
     * @param filePath
     *         文件的路径
     * @param outputPath
     *         输出文件的路径
     * @param power
     *         要做几次方
     * @param lineNums
     *         都有哪几列，需要power，不填默认所有列。从第0列开始
     */
    public void linePower(String filePath, String outputPath, Integer power, Integer... lineNums) throws IOException {
        BufferedReader reader = buildReader(filePath);
        BufferedWriter writer = buildWriter(outputPath);

        addCSVHeader(reader, writer, power, lineNums);

    }

    private Integer[] getLineNums(String[] lines, Integer... lineNums) {
        //为null，则是所有列
        if (lineNums == null) {
            lineNums = new Integer[lines.length];
            for (int i = 0; i < lines.length; i++) {
                lineNums[i] = i;
            }
        }
        return lineNums;
    }

    private List<int[]> getAddList(int power, Integer... lineNums) {
        LineAdder lineAdder = new LineAdder();
        //计算共需增加多少列
        return lineAdder.lineAdd(lineNums.length, power);
    }

    /**
     * 给header里增加相应的列名，都在第一行
     */
    private void addCSVHeader(BufferedReader reader, BufferedWriter writer, Integer power, Integer... lineNums)
            throws IOException {
        //读取第一行
        String header = reader.readLine();
        //所有的列名
        String[] lines = header.split(",");
        lineNums = getLineNums(lines, lineNums);

        //计算共需增加多少列
        List<int[]> list = getAddList(power, lineNums);

        String[] addLines = new String[list.size()];

        String[] needLines = new String[lineNums.length];
        for (int i = 0; i < lineNums.length; i++) {
            needLines[i] = lines[lineNums[i]];
        }
        //设置每一列的名字
        for (int i = 0; i < list.size(); i++) {
            int[] array = list.get(i);
            String s = "";
            for (int j = 0; j < array.length; j++) {
                s += needLines[j] + array[j];
            }
            addLines[i] = s;
        }

        for (String addLine : addLines) {
            header += "," + addLine;
        }
        //将新增的列，写入header文件
        writer.write(header);
        writer.newLine();
        writer.flush();

        String oneLine;

        while ((oneLine = reader.readLine()) != null) {
            addLines = new String[list.size()];
            lines = oneLine.split(",");

            needLines = new String[lineNums.length];
            for (int i = 0; i < lineNums.length; i++) {
                needLines[i] = lines[lineNums[i]];
            }

            //设置每一列的值
            for (int i = 0; i < list.size(); i++) {
                int[] array = list.get(i);
                double s = 1;
                for (int j = 0; j < array.length; j++) {
                    //譬如a，b，对应02时，该列就是a的0次方乘以b的2次方
                    s *= Math.pow(Double.valueOf(needLines[j]), array[j]);
                }
                addLines[i] = s + "";
            }
            for (String addLine : addLines) {
                oneLine += "," + addLine;
            }
            writer.write(oneLine);

            //写入相关文件
            writer.newLine();
        }

        //将新增的列，写入header文件
        writer.flush();
        //关闭流
        reader.close();
        writer.close();
    }

    private BufferedReader buildReader(String filePath) {
        try {
            return new BufferedReader(new FileReader(new File(filePath)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private BufferedWriter buildWriter(String outputPath) {
        //写入相应的文件
        try {
            return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputPath), "utf-8"));
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
