int max = 10;
int arr[10];

void print(int a[], int size)
{
	int i = 0;
	while(i < size) {
		write(a[i]);
		++i;
	}
}

int sum(int a, int b)
{
	return a + b;
}

int fibo(int i)
{
	if(i <= 1 )
		return 1;
	else
		return fibo(i - 1) + fibo(i - 2);
}

void main()
{
	int a;
	int b;
	int c[3];
	int i = 0;

	c[0] = 0;
	c[1] = 1;
	c[2] = 2;

	while(i < max) {
		a = fibo(i);
		write(-a);
		arr[i] = i;
		++i;
	}

	print(arr, max);
	print(c, 3);
	b = sum(c[1], -c[2]);
	write(b);
	a = --b;
	write(a);
	write(b);
	c[2] = ++a;
	write(a);
	write(c[2]);

	write(3 * 2);
	write(4 / 2);
	write(5 % 3);
	write(6 + 3);
	write(3 - 3);
	write(3 == 3);
	write(3 != 3);
	write(3 <= 3);
	write(3 < 3);
	write(3 >= 3);
	write(3 > 3);

	if(3 > 2 and 1 + 2 * 2 == 5) {
		write(100);
		if(3 > 2 and (1 + 2) * 2 == 5) {
			write(100);
		}
		else {
			write(-1);
			if(!(3 > 2 and (1 + 2) * 2 == 5)) {
				write(100);
			}
			else {
				write(-1);
			}
		}
	}
	else {
		write(-1);
	}
}
