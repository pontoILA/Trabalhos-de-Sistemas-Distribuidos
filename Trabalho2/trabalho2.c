#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>
#include <time.h>

int maiorValor(int *vetor, int tamanho){
    int maior = 0;
    for(int i = 0; i < tamanho; i++){
       maior = maior<vetor[i]?vetor[i]:maior;
    }
    return maior;
}

int mediaValores(int*vetor, int tamanho){
    int soma = 0;
    for(int i = 0; i < tamanho; i++){
        soma+=vetor[i];
    }
    return soma/tamanho;
}

int* criarVetor(int tamanho, int numeroMaximo){
    int* vetor= (int *) malloc(tamanho*sizeof(int));
    srand(time(NULL));
    
    for(int i =0; i<tamanho;i++){
        vetor[i] = (int)rand()%numeroMaximo+1;;
    }

    
    return vetor;
}

int main (int argc, char* argv[]){
    int meu_rank, qtd_processos, tamanho, variacao, mestre=0;
    int* vetor;
    float tempo_total =0;

    double local_start, local_finish, local_elapsed, elapsed;

    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &meu_rank);
    MPI_Comm_size(MPI_COMM_WORLD, &qtd_processos);

    tamanho = atoi(argv[1]);
    variacao = atoi(argv[2]);


    if(meu_rank==mestre){//Mestre cria a vetor
        vetor = criarVetor(tamanho*qtd_processos, variacao);
        
    }
    //Criando a largada do cronometro
    MPI_Barrier(MPI_COMM_WORLD);
    local_start= MPI_Wtime();


    //Conjunto é a quantidade do vetor que vai para cada processo 
    int* conjunto = (int*)malloc(tamanho*sizeof(int));

    //Enviando para cada processo uma parte do vetor com tamanho "tamanho"
    MPI_Scatter(vetor,tamanho,MPI_INT, conjunto, tamanho, MPI_INT, mestre, MPI_COMM_WORLD);

    //Pegando o valor maximo de cada conjunto
    int maximo_conjunto = maiorValor(conjunto,tamanho);

    int* maximo_conjuntos = NULL;
    if(meu_rank==mestre){
        maximo_conjuntos = (int*) malloc(sizeof(int)*qtd_processos);
    }

    //Reunindo o maximo de cada processo no processo mestre.
    MPI_Gather(&maximo_conjunto, 1, MPI_INT, maximo_conjuntos, 1, MPI_INT, mestre,MPI_COMM_WORLD);

    if(meu_rank == mestre){
        int media = mediaValores(maximo_conjuntos, qtd_processos);
        printf("A media entre o valor mais alto dos processos é %d\n", media);
    }

    //Final dos processos e parada do cronometro
    local_finish = MPI_Wtime();
    local_elapsed = local_finish - local_start;

    MPI_Reduce(&local_elapsed, &elapsed, 1, MPI_DOUBLE, MPI_MAX, mestre, MPI_COMM_WORLD);

    if(meu_rank == mestre){
        printf("Tempo: %e segundos\n", elapsed);
    }

    //Liberando memoria
    if(meu_rank==mestre){
        free(vetor);
        free(maximo_conjuntos);
    }
    free(conjunto);

    MPI_Barrier(MPI_COMM_WORLD);
    MPI_Finalize();
    return 0;
}