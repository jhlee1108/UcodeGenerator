int a;
int c=4;
int b[20];
void main()
{
	int x;
	int j=3;
	x = 737;
	j = x;
	--x;
	++x;
	x = 3 + j;
	j = 5 * 3;
	while(x > 0) {
		x = x / 2;
		j = j + 1;
		write(j);
	}
}