import * as React from 'react';
import { HogeState } from '../states/hogeState';
import { HogeActions } from '../containers/hogeContainer';
import CalendarHeatmap from 'react-calendar-heatmap';
import 'react-calendar-heatmap/dist/styles.css';
import './hoge.css'

interface OwnProps {}

type HogeProps = OwnProps & HogeState & HogeActions;

export const HogeComponent: React.SFC<HogeProps> = (props: HogeProps) => {
  return (
    <div>
      
      <div className="field">
        <input
          type="text"
          placeholder="name"
          value={props.name}
          onChange={(e) => props.updateName(e.target.value)}
        />
      </div>
      <div className="field">
        <input
          type="email"
          placeholder="email"
          value={props.email}
          onChange={(e) => props.updateEmail(e.target.value)}
        />
      </div>
      <div className="calender">
        <CalendarHeatmap
          startDate={new Date('2016-01-01')}
          endDate={new Date('2017-01-30')}
          values={[
            { date: '2016-01-01',count:2 },
            { date: '2016-01-22' },
            { date: '2016-01-30' },
            // ...and so on
          ]}
        />
      </div>
      <div className="field">
        <input
          type="email"
          placeholder="email"
          value={props.email}
          onChange={(e) => props.updateEmail(e.target.value)}
        />
      </div>
    </div>
  );
};