int decrease(int a)
{
	return a - 1;
}

int increase(int a)
{
	return a + 1;
}

int div(int a, int b)
{
	if(b != 0) {
		return a / b;
	}
}

int sum(int a, int b)
{
	return increase(a) + decrease(b);
}

int average(int a, int b)
{
	int x;
	
	x = sum(a, b);
	x = div(x, 2);
	return x;
}	

void test(int a, int b)
{
	int result;
	
	result = sum(a, b);
	write(result);
	result = average(a, b);
	write(result);
}

int fibonacci(int i)
{
	if(i <= 1 )
		return 1;
	else
		return fibonacci(i - 1) + fibonacci(i - 2);
}

void main()
{
	int a;
	int b;

	a = fibonacci(7);
	b = fibonacci(5);
	test(a, b);
	write(10);
}
