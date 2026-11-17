## A: gcd DU анализ

### Def/Use (файлын мөрөөр)
| Мөр | Код | Def | Use |
|---|---|---|---|
| 13 | gcd(int x, int y) | x, y | |
| 15 | while (y != 0) | | y (p) |
| 16 | tmp = x % y; | tmp | x, y (c) |
| 17 | x = y; | x | y (c) |
| 18 | y = tmp; | y | tmp (c) |
| 20 | return x; | | x (c) |

Мөр 14 (`int tmp;`) зөвхөн зарлалт, def биш.

### DU хосууд
| # | Хувьсагч | Def | Use | Төрөл | Def-clear зам | Тестийн ID | Хамрагдсан |
|---|---|---|---|---|---|---|---|
| 1 | x | 13 | 16 | c | 13→15→16 | gcdTwoIterations | Тийм |
| 2 | x | 13 | 20 | c | 13→15→20 | gcdZeroY | Тийм |
| 3 | x | 17 | 16 | c | 17→18→15→16 | gcdTwoIterations | Тийм |
| 4 | x | 17 | 20 | c | 17→18→15→20 | gcdTwoIterations | Тийм |
| 5 | y | 13 | 15 | p (T) | 13→15 | gcdTwoIterations | Тийм |
| 6 | y | 13 | 15 | p (F) | 13→15 | gcdZeroY | Тийм |
| 7 | y | 13 | 16 | c | 13→15→16 | gcdTwoIterations | Тийм |
| 8 | y | 13 | 17 | c | 13→15→16→17 | gcdTwoIterations | Тийм |
| 9 | y | 18 | 15 | p (T) | 18→15 | gcdTwoIterations | Тийм |
| 10 | y | 18 | 15 | p (F) | 18→15 | gcdTwoIterations | Тийм |
| 11 | y | 18 | 16 | c | 18→15→16 | gcdTwoIterations | Тийм |
| 12 | y | 18 | 17 | c | 18→15→16→17 | gcdTwoIterations | Тийм |
| 13 | tmp | 16 | 18 | c | 16→17→18 | gcdTwoIterations | Тийм |

Нийт 11 хос (p-use-ийн T/F-ийг тусад нь тоовол 13).

### Тестүүд
- `gcdZeroY`: gcd(7,0). Давталтад орохгүй, мөр 15 шууд false. Ганцаараа (x,13,20) ба (y,13,15F)-ийг хамарна.
- `gcdTwoIterations`: gcd(12,8). Давталт 2 удаа (12,8 → 8,4 → 4,0), мөр 15 нийт 3 удаа шалгагдана. Давталтын def-ээс use хүрэх хосууд (x,17,16), (y,18,16) зэрэг 2-р давталтад л хамрагдана.

### B5: JaCoCo

| Үе шат | Тест | BRANCH missed/covered | LINE missed/covered | Эх сурвалж |
|---|---|---|---|---|
| all-defs | 4 (gcd 2 + bonus 2) | 2 / 14 | 2 / 23 | results/jacoco-alldefs.csv |
| эцсийн | 11 (gcd 2 + bonus 9) | 0 / 16 | 1 / 24 | results/jacoco-final.csv |

LINE_MISSED = 1 нь мөр 7 (далд constructor).

all-defs шатанд мөр 32-ын `attendance == null` ба `threshold < 0` гэсэн 2 true салаа
хамрагдаагүй (хамрагдаагүй мөр: 7 далд constructor, 33 `throw`). Ямар ч def-use хос
exception-ы замыг шаарддаггүй тул all-defs хангагдсан ч branch 100% болоогүй.


## C: Fault seeding

| № | Мутаци | Унасан тест | expected / was | Үр дүн | Файл |
|---|---|---|---|---|---|
| M1 | мөр 41 устгасан | allDefsLongStreakThenReset:14 | expected: <8> but was: <5> | Баригдсан | mvn-test-M1.txt |
| M1 | | streakFiveBelowThreshold:50 | expected: <3> but was: <0> | Баригдсан | mvn-test-M1.txt |
| M2 | мөр 43 `best = present;` | (10 тестийн аль нь ч унасангүй) | - | АМЬД | mvn-test-M2.txt |
| M2 | нэмсэн killsWrongVariableMutant | killsWrongVariableMutant:62 | expected: <0> but was: <3> | Баригдсан | mvn-test-M2-kill.txt |
| M3 | `int total = present + best;` | байхгүй | - | АМЬД | mvn-test-M3.txt |

Анхааруулга: The value of the local variable total is not used  Java(536870973)  [Ln 49, Col 13]
Яагаад тест унахгүй вэ: total нь def-тэй ч use-гүй, DU хос огт үүсэхгүй. bonus()-ийн буцаах утгад нөлөөлдөггүй тул assert үүнийг мэдэхгүй.
Яагаад javac анхааруулахгүй вэ: хэрэглэгдээгүй local хувьсагч Java хэлний алдаа биш, compiler анхдагчаар шалгадаггүй.
Ямар багаж барих вэ: static analysis (IDE, SpotBugs, PMD, Checkstyle). Энэ нь data-flow аномали (def-ийн дараа use байхгүй).