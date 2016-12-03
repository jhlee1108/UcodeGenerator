void func(int a[])
{
	a[1] = 3;
}

void main()
{
	int a[3];
	int x;
	a[1] = 2;
	func(a);
	x=a[1];
	write(x);
}