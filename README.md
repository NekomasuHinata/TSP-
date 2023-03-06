TSP遗传算法求解器
该程序使用遗传算法求解旅行商问题（TSP）。

程序结构
TSPGeneticSolver.java：主程序，包含遗传算法的实现。
tsp_data.txt：TSP问题的数据文件。
数据格式
TSP问题的数据文件的格式如下：

第一行是城市数量。
后面的每一行是一个城市的坐标，格式为 城市编号 x坐标 y坐标。

例如，以下是一个包含5个城市的数据文件的示例：
5
1 0.4 0.3
2 0.6 0.4
3 0.2 0.7
4 0.1 0.2
5 0.8 0.9

运行程序
将数据文件 tsp_data.txt 放在与 TSPGeneticSolver.java 相同的目录下。
打开命令行窗口，切换到包含 TSPGeneticSolver.java 的目录下。
编译程序：javac TSPGeneticSolver.java
运行程序：java TSPGeneticSolver
程序将输出每一代的最优解，以及最终的最优解。

遗传算法参数
程序中的遗传算法使用以下参数：

种群大小：100
迭代次数：1000
交叉概率：0.8
变异概率：0.02
这些参数可以在程序中修改。
