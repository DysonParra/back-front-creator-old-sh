  /**
   * @description Obtiene un observable encargado de la evolucion de la entidad que se este ejecutando
   * @return {Observable<boolean>}
   */
  getMedicamentoActualizando$(): Observable<boolean> {
    return this.medicamentoState.getActualizando$();
  }

  /**
   * @description Obtiene un observable con el listado de objetos tipo Medicamento
   * @return {Observable<MedicamentoViewModel[]>}
   */
  getMedicamentos$(): Observable<MedicamentoViewModel[]> {
    // aquí solo pasamos el estado sin proyecciones
    // Puede suceder que sea necesario combinar dos o más flujos y exponerlos a los componentes.
    return this.medicamentoState.getMedicamentos$();
  }

  /**
   * @description Obtiene un listado de objetos tipo Medicamento que seran gestionados por una coleccion de estado
   * @return {Observable<MedicamentoViewModel[]>}
   */
  cargarMedicamentos() {
    console.log('Cargando Medicamentos...');
    return this.medicamentoService.obtenerMedicamentos()
      .pipe(tap(response => this.medicamentoState.setMedicamentos(response)));
  }

  /**
   * @description Obtiene un listado de objetos tipo Medicamento que seran gestionados por una coleccion de estado
   * @return {Observable<MedicamentoViewModel[]>}
   */
  cargarMedicamentosPaginados(size: number, page: number) {
    console.log('Cargando Medicamentos...');
    return this.medicamentoService.obtenerMedicamentosPaginados(size, page)
      .pipe(tap(response => this.medicamentoState.setMedicamentos(response)));
  }

  /**
   * @description Obtiene un listado de objetos tipo Medicamento que seran gestionados por una coleccion de estado
   * @return {Observable<MedicamentoViewModel[]>}
   */
  buscarMedicamentosPaginados(currentSearch: string, size: number, page: number) {
    console.log('Cargando Medicamentos...');
    return this.medicamentoService.buscarMedicamentosPaginados(currentSearch, size, page)
      .pipe(tap(response => this.medicamentoState.setMedicamentos(response)));
  }

  /**
   * @description Obtiene un listado de objetos tipo Medicamento que seran gestionados por una coleccion de estado
   * @return {Observable<MedicamentoViewModel[]>}
   */
   cargarMedicamentosPaginadosHeaders(size: number, page: number) {
    console.log('Cargando Medicamentos...');
    return this.medicamentoService.obtener({
        page: page - 1,
        size: size,
        sort: "desc"}).subscribe(
        (res: ResponseWrapper) => {
          if (res != undefined) {
            this.medicamentoState.setMedicamentos(res.json);
            //Obtengo la cantidad total
            console.log(res.headers.get('X-Total-Count'));
            this.medicamentoState.setCantidadMedicamentos(parseInt(res.headers.get('X-Total-Count')));
          }
          else {
            this.medicamentoState.setMedicamentos(undefined as any);
            console.log(0);
            this.medicamentoState.setCantidadMedicamentos(0);
          }
        },
        (res: ResponseWrapper) => console.log(res.json)
    );
  }

  /**
   * @description Obtiene un listado de objetos tipo Medicamento que seran gestionados por una coleccion de estado
   * @return {Observable<MedicamentoViewModel[]>}
   */
  buscarMedicamentosPaginadosHeaders(currentSearch: string, size: number, page: number) {
    console.log('Cargando Medicamentos...');
    return this.medicamentoService.buscar(currentSearch, {
        page: page - 1,
        size: size,
        sort: "desc"}).subscribe(
        (res: ResponseWrapper) => {
          if (res != undefined) {
            this.medicamentoState.setMedicamentos(res.json);
            //Obtengo la cantidad total
            console.log(res.headers.get('X-Total-Count'));
            this.medicamentoState.setCantidadMedicamentos(parseInt(res.headers.get('X-Total-Count')));
          }
          else {
            this.medicamentoState.setMedicamentos(undefined as any);
            console.log(0);
            this.medicamentoState.setCantidadMedicamentos(0);
          }
        },
        (res: ResponseWrapper) => console.log(res.json)
    );
  }

  // Actualizacion optimista
  // 1. Actualiza el estado de la UI
  // 2. Invoca el API
  /**
   * @description Agrega un nuevo objeto a la coleccion de estado e invoca el API
   */
  agregarMedicamento(requisito: MedicamentoViewModel) {
    return new Observable((observer) => {
      this.medicamentoService.guardarMedicamento(requisito)
        .subscribe(
          (addedCategoryWithId: MedicamentoViewModel) => {
            // success callback - we have id generated by the server, let's update the state
            this.medicamentoState.actualizarMedicamentoXId(requisito, addedCategoryWithId);
            observer.next(addedCategoryWithId);
          },
          (error) => {
            // error callback - we need to rollback the state change
            this.medicamentoState.eliminarMedicamento(requisito);
            console.log(error);
            observer.next(undefined);
          }
        );
      }
    );
  }

  // Actualizacion pesimista
  // 1. Invoca el API
  // 2. Actualiza el estado de la UI
  /**
   * @description Actualiza un objeto invoca el API y actualiza el objeto de la coleccion de estado
   */
  actualizarMedicamento(requisito: MedicamentoViewModel) {
    return new Observable((observer) => {
      this.medicamentoState.setActualizando(true);
      this.medicamentoService.actualizarMedicamento(requisito)
        .subscribe(
          (next) => {
            this.medicamentoState.actualizarMedicamento(requisito)
            observer.next(next);
          }, (error) => {
            console.log(error);
            observer.next(undefined);
          },
          () => this.medicamentoState.setActualizando(false)
        );
    });
  }

  // Eliminación pesimista
  // 1. Invoca el API
  // 2. Actualiza el estado de la UI
  /**
   * @description Elimina un objeto invoca el API y remueve el objeto de la coleccion de estado
   */
  eliminarMedicamento(requisito: MedicamentoViewModel) {
    return new Observable((observer) => {
      this.medicamentoState.setActualizando(true);
      this.medicamentoService.eliminarMedicamento(requisito.intId.toString())
        .subscribe(
          (next) => {
            this.medicamentoState.eliminarMedicamento(requisito)
            observer.next(next);
          }, (error) => {
            console.log(error);
            observer.next(undefined);
          },
          () => this.medicamentoState.setActualizando(false)
        );
    });
  }
