import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMoneyTransfer } from 'app/shared/model/money-transfer.model';
import { MoneyTransferService } from './money-transfer.service';

@Component({
    selector: 'jhi-money-transfer-delete-dialog',
    templateUrl: './money-transfer-delete-dialog.component.html'
})
export class MoneyTransferDeleteDialogComponent {
    moneyTransfer: IMoneyTransfer;

    constructor(
        private moneyTransferService: MoneyTransferService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.moneyTransferService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'moneyTransferListModification',
                content: 'Deleted an moneyTransfer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-money-transfer-delete-popup',
    template: ''
})
export class MoneyTransferDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ moneyTransfer }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MoneyTransferDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.moneyTransfer = moneyTransfer;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
