import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities, reset } from './event.reducer';
import { IEvent } from 'app/shared/model/event.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Event = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );
  const [sorting, setSorting] = useState(false);

  const eventList = useAppSelector(state => state.event.entities);
  const loading = useAppSelector(state => state.event.loading);
  const totalItems = useAppSelector(state => state.event.totalItems);
  const links = useAppSelector(state => state.event.links);
  const entity = useAppSelector(state => state.event.entity);
  const updateSuccess = useAppSelector(state => state.event.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const { match } = props;

  const [filteredEvents, setFilter] = useState(eventList)

  const filter = event => {
    let list: any
    if (event) {
      if (event.target.value === 'all') {
        list = eventList
      }
      else if (event.target.value === 'lastweek') {
        list = eventList.filter(item => getDateDifference(item.date) <= -1 && getDateDifference(item.date) >= -6)
      }
      else if (event.target.value === 'thisweek') {
        list = eventList.filter(item => getDateDifference(item.date) >= 0 && getDateDifference(item.date) <= 6)
      }
      else if (event.target.value === 'nextweek') {
        list = eventList.filter(item => getDateDifference(item.date) >= 7 && getDateDifference(item.date) <= 13)
      }
      else if (event.target.value === 'today') {
        list = eventList.filter(item => getDateDifference(item.date) === 0)
      }
      else if (event.target.value === 'tomorrow') {
        list = eventList.filter(item => getDateDifference(item.date) === 1)
      }
      else if (event.target.value === 'yesterday') {
        list = eventList.filter(item => getDateDifference(item.date) === -1)
      }
    }
    else {
      list = eventList.filter(item => getDateDifference(item.date) === 0)
    }

    setFilter(list)
  };

  return (
    <div>
      <div className="row mb-2 text-lowercase position-sticky">
        <Link to={`${match.url}/new`} className="btn btn-success jh-create-entity col-sm-2 col-3 order-2 " id="jh-create-entity" data-cy="entityCreateButton">
          Add event
        </Link>
        <button value="all" className="btn btn-light filter-button col-sm-2 col-3 smallText" onClick={filter}>
          Show all
        </button>
        <button value="lastweek" className="btn btn-light filter-button col-sm-2 col-3 smallText" onClick={filter}>
          Lastweek
        </button>
        <button value="today" className="btn btn-light filter-button col-sm-2 col-3 smallText" onClick={filter}>
          Today
        </button>
        <button value="tomorrow" className="btn btn-light filter-button col-sm-2 col-3 smallText" onClick={filter}>
          Tomorrow
        </button>
        <button value="thisweek" className="btn btn-light filter-button col-sm-2 col-3 smallText" onClick={filter} >
          Thisweek
        </button>
        <button value="nextweek" className="btn btn-light filter-button col-sm-2 col-3 smallText" onClick={filter} >
          Nextweek
        </button>
        <button value="yesterday" className="btn btn-light filter-button col-sm-2 col-3 smallText" onClick={filter}>
          Yesterday
        </button>
      </div>
      <div className="table-responsive">
        <InfiniteScroll
          pageStart={paginationState.activePage}
          loadMore={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
          threshold={0}
          initialLoad={false}
        >
          {filteredEvents && filteredEvents.length > 0 ? (
            <div className="row p-0 m-0">
              {filteredEvents.map((event, i) => (
                <div key={`entity-${i}`} data-cy="entityTable" className="col-xl-3 col-lg-4 col-md-6 col-xs-12 p-0 m-0">
                  <div className="card p-0 mb-2 rounded shadow-sm p-1 m-0">
                    <div className="card-header d-flex  justify-content-around font-weight-bold p-0 border-bottom">
                      <div className="col-6">
                        <div>
                          {event.name}
                        </div>
                        <div>
                          {event.place ? event.place.name : ''}
                        </div>
                      </div>
                      <div className="col-6">
                        <div>
                          {getDate(event.date)}
                        </div>
                        <div>
                          {event.startTime} to {event.endTime}
                        </div>
                        <div>
                        </div>
                      </div>
                    </div>
                    <div className="card-body p-2 border-bottom position-relative">

                      <div className="d-flex flex-wrap">
                        {event.nonmembers ? event.nonmembers.split(';').map((name, count) => (
                          <strong className="p-1 m-1 rounded border border-primary" key={`entity-${count}`} data-cy="entityTable">
                            {name}
                          </strong>
                        )) : ''}
                        <button className="btn btn-sm">+</button>
                      </div>
                      <div>
                        court: 1 | code: 3456 | cost: {event.cost} | share: {event.share}
                      </div>
                      <div className="d-flex  justify-content-around">
                        <Button tag={Link} to={`${match.url}/${event.id}/edit`} color="info" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${event.id}/delete`} color="primary" size="sm" data-cy="entityDeleteButton">
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                        <button className="btn btn-sm border-dark rounded"> confirm booking</button>
                      </div>
                    </div>
                    <small className="text-muted">Last updated today 7.00 am</small>
                  </div>
                </div>
              ))}
            </div>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="kompiApp.event.home.notFound">No Events found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Event;

function getDateDifference(date): number {
  date = new Date(Date.parse(date))
  const today = new Date()
  const dateDifference = date.getDate() - today.getDate()
  return dateDifference
}

const days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];

function getDate(date) {
  date = new Date(Date.parse(date))
  const today = new Date()
  let day_string



  if (date.getFullYear() === today.getFullYear()) {
    if (date.getMonth() === today.getMonth()) {
      const dateDifference = date.getDate() - today.getDate()
      if (dateDifference === 0) {
        return 'Today'
      }
      else if (dateDifference === 1) {
        return 'Tomorrow'
      }
      else if (dateDifference === -1) {
        return 'Yesterday'
      }
      else if (dateDifference > 1 && dateDifference <= 6) {
        return 'This ' + days[date.getDay()]
      }
      else if (dateDifference >= 7 && dateDifference <= 13) {
        return 'Next ' + days[date.getDay()]
      }
      else if (dateDifference <= -1 && dateDifference > -7) {
        return 'Last ' + days[date.getDay()]
      }
      else {
        return date
      }
    }
  }
  else {
    return date ? <TextFormat type="date" value={date} format={APP_DATE_FORMAT} /> : null
  }
}
