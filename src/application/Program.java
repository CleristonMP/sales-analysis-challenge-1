package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Sale;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.next();
		System.out.println();

		List<Sale> list = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = br.readLine();
			while (line != null) {
				String[] saleInfo = line.split(",");
				Sale sale = new Sale(
						Integer.parseInt(saleInfo[0]), 
						Integer.parseInt(saleInfo[1]), 
						saleInfo[2],
						Integer.parseInt(saleInfo[3]), 
						Double.parseDouble(saleInfo[4]));
				list.add(sale);
				line = br.readLine();
			}
			System.out.println("Cinco primeiras vendas de 2016 de maior preço médio");
			List<Sale> newList = list.stream().filter(s -> s.getYear() == 2016)
					.sorted((s1, s2) -> -s1.averagePrice().compareTo(s2.averagePrice())).limit(5)
					.collect(Collectors.toList());
			newList.forEach(System.out::println);
			System.out.println();

			Double loganSales = list.stream().filter(s -> s.getSeller().equals("Logan"))
					.filter(s -> s.getMonth().equals(1) || s.getMonth().equals(7)).map(s -> s.getTotal())
					.reduce(0.0, (x, y) -> x + y);

			System.out.printf("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = %.2f%n", loganSales);
		} 
		catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		} 
		finally {
			if (sc != null) {
				sc.close();
			}
		}
	}
}
