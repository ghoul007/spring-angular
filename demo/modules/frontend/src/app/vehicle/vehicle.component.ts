import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { PagedResponse } from '../models/paged-response-model';
import { Vehicle } from '../models/vehicle.model';
import { StompService } from '../services/stomp.service';

@Component({
  selector: 'app-vehicle',
  templateUrl: './vehicle.component.html',
  styleUrls: ['./vehicle.component.css']
})
export class VehicleComponent implements OnInit {
  displayedColumns: string[] = ['id', 'number', 'created', 'modified', 'action'];
  page: number = 0;
  size: number = 4;
  totalCount:number = 0;

  vehicleForm = new FormGroup({
    number: new FormControl(null),
  });

  vehicles:Vehicle[] = [];

  constructor(private http: HttpClient, private stompService: StompService) {}

  ngOnInit(): void {
    this.refreshVehicleTable(this.page, this.size);

    this.stompService.subscribe('/topic/vehicle', (): void => {
      this.refreshVehicleTable(this.page, this.size);
    });
  }

  onSubmit() {
    this.http.post<Vehicle>("/api/vehicules", this.vehicleForm.value).subscribe((response:Vehicle) => {
      if (!!response) {
        console.log("Created vehicle " + response.id + " " + response.number);
      }
    });
  }

  delete(vehicleId:number | undefined):void {
    this.http.delete("/api/vehicules/" + vehicleId).subscribe((response:any) => {
      console.log("Deleted vehicle with id " + vehicleId + " " + response);
    })
  }

  loadVehicles(event:PageEvent): PageEvent {
    this.page = event.pageIndex;
    this.size = event.pageSize;

    this.refreshVehicleTable(this.page, this.size);
    return event;
  }

  private refreshVehicleTable(page?:number, size?:number): void {
    this.vehicles = [];
    const sufix:string = (page !== undefined && size != undefined)
        ? "?page=" + page + "&size=" + size
        : "";

    this.http.get<PagedResponse<Vehicle>>("/api/vehicules/list" + sufix).subscribe((response:PagedResponse<Vehicle>) => {
      this.vehicles = !!response.content ? response.content : [];
      this.totalCount = !!response.totalCount ? response.totalCount : 0;
    });
  }
}