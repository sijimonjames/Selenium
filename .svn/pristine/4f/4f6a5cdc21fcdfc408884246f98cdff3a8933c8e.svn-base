
package com.acneuro.test.automation.libraries;

public class RioGenerator {
	static char[] map = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+' };

	public static void main(String[] args) {
		String msisdn = args[0];
		String rio = args[1];
		String opCode = args[2];
		System.out.println(RioGenerator.generateRio(opCode, rio.substring(2, 3), rio.substring(3, 9), msisdn));
	}

	public static String generateRio(String opcode, String subscriberType, String customerReference, String msisdn) {
		if (opcode.length() != 2 || subscriberType.length() != 1 || customerReference.length() != 6
				|| msisdn.length() != 10) {
			return null;
		}
		String fullString = String.valueOf(opcode) + subscriberType + customerReference + msisdn;
		char[] phase1 = fullString.toCharArray();
		int res1 = RioGenerator.Res1(18, phase1);
		int res2 = RioGenerator.Res2(18, phase1);
		int res3 = RioGenerator.Res3(18, phase1);
		return String.valueOf(opcode) + subscriberType + customerReference + map[res1] + map[res2] + map[res3];
	}

	private static int Res1(int at, char[] phase1) {
		if (at < 0) {
			return 0;
		}
		int order = 0;
		int i = 0;
		while (i < map.length) {
			if (map[i] == phase1[at]) {
				order = i;
				break;
			}
			++i;
		}
		return (RioGenerator.Res1(at - 1, phase1) + order) % 37;
	}

	private static int Res2(int at, char[] phase1) {
		if (at < 0) {
			return 0;
		}
		int order = 0;
		int i = 0;
		while (i < map.length) {
			if (map[i] == phase1[at]) {
				order = i;
				break;
			}
			++i;
		}
		return (2 * RioGenerator.Res2(at - 1, phase1) + order) % 37;
	}

	private static int Res3(int at, char[] phase1) {
		if (at < 0) {
			return 0;
		}
		int order = 0;
		int i = 0;
		while (i < map.length) {
			if (map[i] == phase1[at]) {
				order = i;
				break;
			}
			++i;
		}
		return (4 * RioGenerator.Res3(at - 1, phase1) + order) % 37;
	}
}