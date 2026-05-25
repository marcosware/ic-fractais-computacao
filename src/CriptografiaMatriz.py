#conversão caractere pra numero
def charParaNumero(c):
    if c == ' ':
        return 0
    return ord(c.upper()) - ord('A') + 1


#conversão numero pra caractere
def numeroParaChar(n):
    if n == 0:
        return ' '
    return chr(ord('A') + n - 1)


#converte string pra uma matriz 3x3
def stringParaMatriz(s):
    m = [[0 for j in range(3)] for i in range(3)]

    while len(s) < 9:
        s += " "

    k = 0
    for i in range(3):
        for j in range(3):
            m[i][j] = charParaNumero(s[k])
            k += 1

    return m


#converte a matriz pra uma string
def matrizParaString(m):
    resultado = ""

    for i in range(3):
        for j in range(3):
            resultado += numeroParaChar(m[i][j])

    return resultado


#multiplica as matrizes
def multiplicar(A, B):
    R = [[0 for j in range(3)] for i in range(3)]

    for i in range(3):
        for j in range(3):
            R[i][j] = 0
            for k in range(3):
                R[i][j] += A[i][k] * B[k][j]

    return R


#Calcula determinante
def determinante(m):
    return (
        m[0][0] * (m[1][1] * m[2][2] - m[1][2] * m[2][1])
        - m[0][1] * (m[1][0] * m[2][2] - m[1][2] * m[2][0])
        + m[0][2] * (m[1][0] * m[2][1] - m[1][1] * m[2][0])
    )


#Calcula a matriz inversa
def inversa(m):
    inv = [[0 for j in range(3)] for i in range(3)]
    det = determinante(m)

    if det == 0:
        print("Matriz nao invertivel")
        return None

    inv[0][0] = (m[1][1] * m[2][2] - m[1][2] * m[2][1])
    inv[0][1] = -(m[0][1] * m[2][2] - m[0][2] * m[2][1])
    inv[0][2] = (m[0][1] * m[1][2] - m[0][2] * m[1][1])

    inv[1][0] = -(m[1][0] * m[2][2] - m[1][2] * m[2][0])
    inv[1][1] = (m[0][0] * m[2][2] - m[0][2] * m[2][0])
    inv[1][2] = -(m[0][0] * m[1][2] - m[0][2] * m[1][0])

    inv[2][0] = (m[1][0] * m[2][1] - m[1][1] * m[2][0])
    inv[2][1] = -(m[0][0] * m[2][1] - m[0][1] * m[2][0])
    inv[2][2] = (m[0][0] * m[1][1] - m[0][1] * m[1][0])

    for i in range(3):
        for j in range(3):
            inv[i][j] /= det

    return inv


#multiplica pela inversa
def multiplicarComInversa(A, B):
    R = [[0 for j in range(3)] for i in range(3)]

    for i in range(3):
        for j in range(3):
            soma = 0

            for k in range(3):
                soma += A[i][k] * B[k][j]

            R[i][j] = round(soma)

    return R

#le a matriz
def lerMatriz():
    m = [[0 for j in range(3)] for i in range(3)]

    for i in range(3):
        linha = input().split()

        while len(linha) < 3:
            linha += input().split()

        for j in range(3):
            m[i][j] = int(linha[j])

    return m

#print da matriz
def mostrar(m):
    for i in range(3):
        print("[ ", end="")

        for j in range(3):
            print(str(m[i][j]) + " ", end="")

        print("]")


op = 0

print("1 - Codificar")
print("2 - Decodificar")
op = int(input())

if op == 1:
    print("Digite a mensagem (max 9 chars): ", end="")
    msg = input()

    if len(msg) > 9:
        msg = msg[:9]

    M = stringParaMatriz(msg)

    print("Digite a matriz chave:")
    K = lerMatriz()

    C = multiplicar(M, K)

    print("Mensagem codificada:")
    mostrar(C)

elif op == 2:
    print("Digite a matriz codificada:")
    C = lerMatriz()

    print("Digite a matriz chave:")
    K = lerMatriz()

    inv = inversa(K)

    if inv != None:
        M = multiplicarComInversa(C, inv)

        msg = matrizParaString(M)

        print("Mensagem decodificada:")
        print(msg)


"""
    MATRIZ CHAVE DE EXEMPLO:
    1 0 1
    1 3 1
    0 1 1

    Pra usar uma matriz como chave, ela tem que ser invertível
    (Honestamente, não sei como saber se uma matriz é invertível.)
""" 